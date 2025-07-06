package com.lautaro.osito_store.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lautaro.osito_store.dto.CartDTO;
import com.lautaro.osito_store.entity.Cart;
import com.lautaro.osito_store.entity.CartItem;
import com.lautaro.osito_store.entity.User;

@Component
public class CartMapper {

    @Autowired
    private CartItemMapper cartItemMapper;

    public CartDTO toDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setTotal(cart.getTotal());

        if (cart.getUser() != null) {
            cartDTO.setUserId(cart.getUser().getId());
        }

        if (cart.getItems() != null) {
            cartDTO.setItems(cart.getItems().stream()
                .map(cartItemMapper::toDTO)
                .toList());
        } else {
            cartDTO.setItems(new ArrayList<>());
        }

        return cartDTO;
    }

    public Cart toEntity(CartDTO cartDTO, User user, List<CartItem> items) {
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        cart.setTotal(cartDTO.getTotal());
        cart.setUser(user);
        cart.setItems(items != null ? items : new ArrayList<>());
        return cart;
    }

}
