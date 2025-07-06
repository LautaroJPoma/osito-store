package com.lautaro.osito_store.service;

import java.util.List;

import com.lautaro.osito_store.dto.CartDTO;
import com.lautaro.osito_store.entity.Cart;

public interface CartService {

    CartDTO createCart(CartDTO cartDTO);

    List<CartDTO> getAllCarts();

    CartDTO getCartById(Long id);

    CartDTO updateCart(Long id, CartDTO cartDTO);

    void updateCartTotal(Cart cart);

    void deleteCart(Long id);
    
    boolean existsById(Long id);

}
