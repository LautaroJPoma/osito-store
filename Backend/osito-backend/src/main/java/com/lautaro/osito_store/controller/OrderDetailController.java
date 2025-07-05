package com.lautaro.osito_store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lautaro.osito_store.dto.OrderDetailDTO;
import com.lautaro.osito_store.service.OrderDetailService;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        List<OrderDetailDTO> details = orderDetailService.getAllOrderDetails();
        return ResponseEntity.ok(details);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderDetailById(@PathVariable Long id) {
        if (!orderDetailService.existsById(id)) {
            throw new RuntimeException("Detalle con ID " + id + " no encontrado");
        }
        return ResponseEntity.ok(orderDetailService.getOrderDetailById(id));
    }

    @PostMapping
    public ResponseEntity<OrderDetailDTO> createOrderDetail(@RequestBody OrderDetailDTO dto) {
        if (dto.getPurchaseOrderId() == null) {
            throw new RuntimeException("El ID de la orden de compra es obligatorio");
        }
        if (dto.getVariantId() == null) {
            throw new RuntimeException("El ID de la variante del producto es obligatorio");
        }

        OrderDetailDTO created = orderDetailService.createOrderDetail(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetailDTO dto) {
        if (!orderDetailService.existsById(id)) {
            throw new RuntimeException("Detalle con ID " + id + " no encontrado");
        }

        OrderDetailDTO updated = orderDetailService.updateOrderDetail(dto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        if (!orderDetailService.existsById(id)) {
            throw new RuntimeException("Detalle con ID " + id + " no encontrado");
        }
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }

}
