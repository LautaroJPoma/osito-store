package com.lautaro.osito_store.dto;

public class OrderDetailDTO {

    private Long id;

    private Integer quantity;

    private Long purchaseOrderId;

    private Long variantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setProductId(Long variantId) {
        this.variantId = variantId;
    }

    

}
