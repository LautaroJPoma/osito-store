package com.lautaro.osito_store.service;

import java.util.List;

import com.lautaro.osito_store.dto.PurchaseOrderDTO;
import com.lautaro.osito_store.entity.PurchaseOrder;

public interface PurchaseOrderService {

    List<PurchaseOrderDTO> getAllPurchaseOrders();

    PurchaseOrderDTO getPurchaseOrderById(Long id);

    PurchaseOrderDTO createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO);

    PurchaseOrderDTO updatePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO, Long id);

    void updateTotal(PurchaseOrder purchaseOrder);

    void deletePurchaseOrder(Long id);

}
