package com.lautaro.osito_store.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lautaro.osito_store.dto.OrderDetailDTO;
import com.lautaro.osito_store.entity.OrderDetail;
import com.lautaro.osito_store.entity.ProductVariant;
import com.lautaro.osito_store.entity.PurchaseOrder;
import com.lautaro.osito_store.mapper.OrderDetailMapper;
import com.lautaro.osito_store.repository.OrderDetailRepository;

import com.lautaro.osito_store.repository.ProductVariantRepository;
import com.lautaro.osito_store.repository.PurchaseOrderRepository;
import com.lautaro.osito_store.service.OrderDetailService;
import com.lautaro.osito_store.service.PurchaseOrderService;

import jakarta.transaction.Transactional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductVariantRepository productVariantRepository;
    private final PurchaseOrderService purchaseOrderService;

    public OrderDetailServiceImpl(OrderDetailMapper orderDetailMapper,
            OrderDetailRepository orderDetailRepository,
            PurchaseOrderRepository purchaseOrderRepository,
            ProductVariantRepository productVariantRepository,
            PurchaseOrderService purchaseOrderService) {
        this.orderDetailMapper = orderDetailMapper;
        this.orderDetailRepository = orderDetailRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productVariantRepository = productVariantRepository;
        this.purchaseOrderService = purchaseOrderService;
    }

    @Transactional
    @Override
    public OrderDetailDTO createOrderDetail(OrderDetailDTO dto) {
        OrderDetail orderDetail = orderDetailMapper.toEntity(dto);

        if (orderDetail.getQuantity() == null) {
            orderDetail.setQuantity(1);
        }

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(dto.getPurchaseOrderId())
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada"));
        orderDetail.setPurchaseOrder(purchaseOrder);

        ProductVariant productVariant = productVariantRepository.findById(dto.getVariantId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        orderDetail.setProductVariant(productVariant);

        OrderDetail saved = orderDetailRepository.save(orderDetail);
        purchaseOrder.getOrderDetail().add(saved);

        purchaseOrderService.updateTotal(purchaseOrder);

        return orderDetailMapper.toDTO(saved);
    }

    @Transactional
    @Override
    public OrderDetailDTO addOrderDetailToPurchaseOrder(Long purchaseOrderId, OrderDetailDTO dto) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada"));

        OrderDetail orderDetail = orderDetailMapper.toEntity(dto);
        orderDetail.setPurchaseOrder(purchaseOrder);

        ProductVariant variant = productVariantRepository.findById(dto.getVariantId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        orderDetail.setProductVariant(variant);

        if (orderDetail.getQuantity() == null) {
            orderDetail.setQuantity(1);
        }

        OrderDetail saved = orderDetailRepository.save(orderDetail);
        purchaseOrder.getOrderDetail().add(saved);

        purchaseOrderService.updateTotal(purchaseOrder);

        return orderDetailMapper.toDTO(saved);
    }

    @Override
    public OrderDetailDTO getOrderDetailById(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
        return orderDetailMapper.toDTO(orderDetail);
    }

    @Override
    public List<OrderDetailDTO> getAllOrderDetails() {
        return orderDetailRepository.findAll().stream()
                .map(orderDetailMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public OrderDetailDTO updateOrderDetail(OrderDetailDTO dto, Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

        orderDetail.setQuantity(dto.getQuantity());

        if (dto.getPurchaseOrderId() != null) {
            PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(dto.getPurchaseOrderId())
                    .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada"));
            orderDetail.setPurchaseOrder(purchaseOrder);
        }

        OrderDetail updated = orderDetailRepository.save(orderDetail);
        purchaseOrderService.updateTotal(orderDetail.getPurchaseOrder());

        return orderDetailMapper.toDTO(updated);
    }

    @Transactional
    @Override
    public void deleteOrderDetail(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

        PurchaseOrder purchaseOrder = orderDetail.getPurchaseOrder();

        orderDetailRepository.delete(orderDetail);

        if (purchaseOrder != null) {
            purchaseOrderService.updateTotal(purchaseOrder);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return orderDetailRepository.existsById(id);
    }
}
