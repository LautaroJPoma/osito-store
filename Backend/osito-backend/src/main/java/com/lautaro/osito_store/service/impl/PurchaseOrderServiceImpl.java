package com.lautaro.osito_store.service.impl;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.lautaro.osito_store.dto.OrderDetailDTO;
import com.lautaro.osito_store.dto.PurchaseOrderDTO;
import com.lautaro.osito_store.entity.Cart;
import com.lautaro.osito_store.entity.OrderDetail;
import com.lautaro.osito_store.entity.Post;
import com.lautaro.osito_store.entity.ProductVariant;
import com.lautaro.osito_store.entity.PurchaseOrder;
import com.lautaro.osito_store.entity.User;
import com.lautaro.osito_store.enums.PurchaseOrderStatus;
import com.lautaro.osito_store.mapper.PurchaseOrderMapper;
import com.lautaro.osito_store.repository.CartItemRepository;
import com.lautaro.osito_store.repository.CartRepository;
import com.lautaro.osito_store.repository.OrderDetailRepository;
import com.lautaro.osito_store.repository.PurchaseOrderRepository;
import com.lautaro.osito_store.repository.UserRepository;
import com.lautaro.osito_store.service.OrderDetailService;
import com.lautaro.osito_store.service.PurchaseOrderService;

import jakarta.transaction.Transactional;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final UserRepository userRepository;
    private final OrderDetailService orderDetailService;
    private final CartRepository cartRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartItemRepository cartItemRepository;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository,
            PurchaseOrderMapper purchaseOrderMapper,
            UserRepository userRepository,
            @Lazy  OrderDetailService orderDetailService,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            OrderDetailRepository orderDetailRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.userRepository = userRepository;
        this.orderDetailService = orderDetailService;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Transactional
    @Override
    public PurchaseOrderDTO createPurchaseOrder(PurchaseOrderDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        PurchaseOrder purchaseOrder = purchaseOrderMapper.toEntity(dto, user);
        purchaseOrder.setTotal(0.0);
        purchaseOrder.setStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder saved = purchaseOrderRepository.save(purchaseOrder);

        if (dto.getOrderDetail() != null && !dto.getOrderDetail().isEmpty()) {
            for (OrderDetailDTO detailDTO : dto.getOrderDetail()) {
                detailDTO.setPurchaseOrderId(saved.getId());
                orderDetailService.createOrderDetail(detailDTO);
            }
            updateTotal(saved);
        }

        return purchaseOrderMapper.toDTO(saved);

    }

    @Transactional
    @Override
    public PurchaseOrderDTO createOrderFromCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No se encontró el carrito"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío");
        }

        PurchaseOrder order = new PurchaseOrder();
        order.setUser(cart.getUser());
        order.setStatus(PurchaseOrderStatus.PENDING);

        List<OrderDetail> orderDetails = cart.getItems().stream().map(item -> {
            OrderDetail detail = new OrderDetail();
            detail.setProductVariant(item.getProductVariant());
            detail.setQuantity(item.getQuantity());
            detail.setPurchaseOrder(order);
            return detail;
        }).toList();

        order.setOrderDetail(orderDetails);

        double total = orderDetails.stream()
                .mapToDouble(d -> d.getProductVariant().getPost().getPrice() * d.getQuantity())
                .sum();
        order.setTotal(total);

        PurchaseOrder savedOrder = purchaseOrderRepository.save(order);
        orderDetailRepository.saveAll(orderDetails);

        cartItemRepository.deleteAll(cart.getItems());

        return purchaseOrderMapper.toDTO(savedOrder);
    }

    @Override
    public PurchaseOrderDTO getPurchaseOrderById(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada"));

        return purchaseOrderMapper.toDTO(purchaseOrder);
    }

    @Override
    public List<PurchaseOrderDTO> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll()
                .stream()
                .map(purchaseOrderMapper::toDTO)
                .toList();
    }

    @Transactional
    @Override
    public PurchaseOrderDTO updatePurchaseOrder(PurchaseOrderDTO dto, Long id) {
        PurchaseOrder existing = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada"));

        existing.setTotal(dto.getTotal());

        if (dto.getUserId() != null && existing.getUser() == null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            existing.setUser(user);

        }

        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }

        PurchaseOrder updated = purchaseOrderRepository.save(existing);
        return purchaseOrderMapper.toDTO(updated);
    }

    @Transactional
    @Override
    public void deletePurchaseOrder(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada"));

        purchaseOrderRepository.delete(purchaseOrder);
    }

    @Transactional
    @Override
    public void updateTotal(PurchaseOrder purchaseOrder) {
        double total = purchaseOrder.getOrderDetail()
                .stream()
                .mapToDouble(detail -> {
                    ProductVariant variant = detail.getProductVariant();
                    Post post = variant.getPost();
                    return post != null ? post.getPrice() * detail.getQuantity() : 0.0;
                })
                .sum();
        purchaseOrder.setTotal(total);
        purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    public boolean existsById(Long id) {
        return purchaseOrderRepository.existsById(id);
    }

}
