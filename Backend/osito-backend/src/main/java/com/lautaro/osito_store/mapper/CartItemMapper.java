package com.lautaro.osito_store.mapper;

import org.springframework.stereotype.Component;

import com.lautaro.osito_store.dto.CartItemDTO;
import com.lautaro.osito_store.entity.Cart;
import com.lautaro.osito_store.entity.CartItem;
import com.lautaro.osito_store.entity.ProductVariant;

@Component
public class CartItemMapper {

    public CartItemDTO toDTO(CartItem cartItem){
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());

        if (cartItem.getCart() != null){
            cartItemDTO.setCartId(cartItem.getCart().getId());
        }

        if (cartItem.getProductVariant() != null) {
            cartItemDTO.setProductVariantId(cartItem.getProductVariant().getId());
        }

        return cartItemDTO;

    }

    public CartItem toEntity(CartItemDTO cartItemDTO, Cart cart, ProductVariant productVariant) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDTO.getId());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setCart(cart);
        cartItem.setProductVariant(productVariant);

        return cartItem;
    }
}
