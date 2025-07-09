package com.lautaro.osito_store.dto;

import java.util.List;

import com.lautaro.osito_store.enums.PurchaseOrderStatus;

public class PurchaseOrderDTO {

    private Long id;

    private Double total;

    private Long userId;

    private List<OrderDetailDTO> orderDetail;

    private PurchaseOrderStatus status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderDetailDTO> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public PurchaseOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseOrderStatus status) {
        this.status = status;
    }

    

}
