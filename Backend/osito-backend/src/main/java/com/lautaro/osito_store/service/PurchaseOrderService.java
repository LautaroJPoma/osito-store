package com.lautaro.osito_store.service;

import java.util.List;

import com.lautaro.osito_store.dto.PurchaseOrderDTO;

public interface PurchaseOrderService {

    List<PurchaseOrderDTO> getAllPurchaseOrders();

    PurchaseOrderDTO getPurchaseOrderById(Long id);

    PurchaseOrderDTO createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO);

    PurchaseOrderDTO updatePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO, Long id);

    void deletePurchaseOrder(Long id);

}
