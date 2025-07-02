package com.lautaro.osito_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lautaro.osito_store.entity.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

}
