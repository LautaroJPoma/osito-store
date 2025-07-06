package com.lautaro.osito_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lautaro.osito_store.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    // Additional query methods can be defined here if needed

}
