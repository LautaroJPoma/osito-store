package com.lautaro.osito_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lautaro.osito_store.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {


}
