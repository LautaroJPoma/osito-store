package com.lautaro.osito_store.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lautaro.osito_store.dto.PurchaseOrderDTO;
import com.lautaro.osito_store.entity.Post;
import com.lautaro.osito_store.entity.ProductVariant;
import com.lautaro.osito_store.entity.PurchaseOrder;
import com.lautaro.osito_store.entity.User;
import com.lautaro.osito_store.mapper.PurchaseOrderMapper;
import com.lautaro.osito_store.repository.PurchaseOrderRepository;
import com.lautaro.osito_store.repository.UserRepository;
import com.lautaro.osito_store.service.PurchaseOrderService;

import jakarta.transaction.Transactional;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final UserRepository userRepository;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository,
            PurchaseOrderMapper purchaseOrderMapper,
            UserRepository userRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.userRepository = userRepository;
    }


    @Transactional
    @Override 
    public PurchaseOrderDTO createPurchaseOrder(PurchaseOrderDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        PurchaseOrder purchaseOrder = purchaseOrderMapper.toEntity(dto, user);
        purchaseOrder.setTotal(0.0);

        PurchaseOrder saved = purchaseOrderRepository.save(purchaseOrder);
        return purchaseOrderMapper.toDTO(saved);
    
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
    public PurchaseOrderDTO updatePurchaseOrder( PurchaseOrderDTO dto,Long id) {
        PurchaseOrder existing = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada"));

        existing.setTotal(dto.getTotal());

        if(dto.getUserId() != null && existing.getUser() == null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            existing.setUser(user);

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

}
