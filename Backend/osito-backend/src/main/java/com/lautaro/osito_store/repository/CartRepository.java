package com.lautaro.osito_store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lautaro.osito_store.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
Optional<Cart> findByUserId(Long userId);


}
