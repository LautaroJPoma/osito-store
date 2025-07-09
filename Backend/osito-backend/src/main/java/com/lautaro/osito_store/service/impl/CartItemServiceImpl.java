package com.lautaro.osito_store.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lautaro.osito_store.dto.CartItemDTO;
import com.lautaro.osito_store.entity.Cart;
import com.lautaro.osito_store.entity.CartItem;
import com.lautaro.osito_store.entity.ProductVariant;
import com.lautaro.osito_store.mapper.CartItemMapper;
import com.lautaro.osito_store.repository.CartItemRepository;
import com.lautaro.osito_store.repository.CartRepository;
import com.lautaro.osito_store.repository.ProductVariantRepository;
import com.lautaro.osito_store.service.CartItemService;
import com.lautaro.osito_store.service.CartService;

import jakarta.transaction.Transactional;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CartItemMapper cartItemMapper;
    private final CartService cartService;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartRepository cartRepository,
            ProductVariantRepository productVariantRepository, CartItemMapper cartItemMapper,
            CartService cartService) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productVariantRepository = productVariantRepository;
        this.cartItemMapper = cartItemMapper;
        this.cartService = cartService;
    }

    @Transactional
    @Override
    public CartItemDTO createCartItem(CartItemDTO cartItemDTO) {
        Cart cart = cartRepository.findById(cartItemDTO.getCartId())
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        ProductVariant productVariant = productVariantRepository.findById(cartItemDTO.getProductVariantId())
                .orElseThrow(() -> new RuntimeException("Variante de producto no encontrada"));

        CartItem cartItem = cartItemMapper.toEntity(cartItemDTO, cart, productVariant);
        if (cartItem.getQuantity() == null || cartItem.getQuantity() <= 0) {
            cartItem.setQuantity(1);
        }

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        cart.getItems().add(savedCartItem);

        cartService.updateCartTotal(cart);
        return cartItemMapper.toDTO(savedCartItem);

    }

    @Override
    public CartItemDTO getCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        return cartItemMapper.toDTO(cartItem);
    }

    @Override
    public List<CartItemDTO> getAllCartItems() {
        return cartItemRepository.findAll().stream()
                .map(cartItemMapper::toDTO)
                .toList();
    }

    @Transactional
    @Override
    public CartItemDTO updateCartItem(Long id, CartItemDTO cartItemDTO) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        cartItem.setQuantity(cartItemDTO.getQuantity());

        CartItem updatedCartItem = cartItemRepository.save(cartItem);
        cartService.updateCartTotal(cartItem.getCart());
        return cartItemMapper.toDTO(updatedCartItem);
    }

    @Transactional
    @Override
    public void deleteCartItem(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        Cart cart = cartItem.getCart();
        cartItemRepository.delete(cartItem);

        if (cart != null) {
            cartService.updateCartTotal(cart);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return cartItemRepository.existsById(id);
    }


}
