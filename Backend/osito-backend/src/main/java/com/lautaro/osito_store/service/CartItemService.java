package com.lautaro.osito_store.service;

import java.util.List;

import com.lautaro.osito_store.dto.CartItemDTO;


public interface CartItemService {
    CartItemDTO createCartItem(CartItemDTO cartItemDTO);

    List<CartItemDTO> getAllCartItems();

    CartItemDTO getCartItemById(Long id);

    CartItemDTO updateCartItem(Long id, CartItemDTO cartItemDTO);

    void deleteCartItem(Long id);
    
    boolean existsById(Long id);
   

}
