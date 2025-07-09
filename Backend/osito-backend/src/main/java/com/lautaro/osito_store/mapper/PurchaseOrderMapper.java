package com.lautaro.osito_store.mapper;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Component;

import com.lautaro.osito_store.dto.OrderDetailDTO;
import com.lautaro.osito_store.dto.PurchaseOrderDTO;
import com.lautaro.osito_store.entity.OrderDetail;
import com.lautaro.osito_store.entity.PurchaseOrder;
import com.lautaro.osito_store.entity.User;

@Component
public class PurchaseOrderMapper {

    private final OrderDetailMapper orderDetailMapper;

    public PurchaseOrderMapper(OrderDetailMapper orderDetailMapper) {
        this.orderDetailMapper = orderDetailMapper;
    }

    public PurchaseOrderDTO toDTO(PurchaseOrder purchaseOrder) {
        PurchaseOrderDTO purchaseOrderDto = new PurchaseOrderDTO();
        purchaseOrderDto.setId(purchaseOrder.getId());
        purchaseOrderDto.setStatus(purchaseOrder.getStatus());
        purchaseOrderDto.setTotal(purchaseOrder.getTotal());

        if (purchaseOrder.getUser() != null) {
            purchaseOrderDto.setUserId(purchaseOrder.getUser().getId());
        }

        if (purchaseOrder.getOrderDetail() != null) {
            List<OrderDetailDTO> detailDTOs = purchaseOrder.getOrderDetail()
                .stream()
                .map(orderDetailMapper::toDTO)
                .toList();
            purchaseOrderDto.setOrderDetail(detailDTOs);
        } else {
            purchaseOrderDto.setOrderDetail(new ArrayList<>());
        }

        return purchaseOrderDto;
    }

    public PurchaseOrder toEntity(PurchaseOrderDTO purchaseOrderDto, User user) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(purchaseOrderDto.getId());
        purchaseOrder.setStatus(purchaseOrderDto.getStatus());
        purchaseOrder.setTotal(purchaseOrderDto.getTotal());
        purchaseOrder.setUser(user);

        if (purchaseOrderDto.getOrderDetail() != null) {
            List<OrderDetail> details = purchaseOrderDto.getOrderDetail()
                .stream()
                .map(orderDetailMapper::toEntity)
                .toList();
            purchaseOrder.setOrderDetail(details);
        } else {
            purchaseOrder.setOrderDetail(new ArrayList<>());
        }

        return purchaseOrder;
    }
}

