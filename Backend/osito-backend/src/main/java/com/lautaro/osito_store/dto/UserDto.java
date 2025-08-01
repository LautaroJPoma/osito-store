package com.lautaro.osito_store.dto;

import java.util.List;

public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private List<Long> purchaseOrderIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getPurchaseOrderIds() {
        return purchaseOrderIds;
    }

    public void setPurchaseOrderIds(List<Long> purchaseOrderIds) {
        this.purchaseOrderIds = purchaseOrderIds;
    }

    


}
