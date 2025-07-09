package com.lautaro.osito_store.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lautaro.osito_store.dto.CartDTO;
import com.lautaro.osito_store.entity.Cart;
import com.lautaro.osito_store.entity.Post;
import com.lautaro.osito_store.entity.ProductVariant;
import com.lautaro.osito_store.entity.User;
import com.lautaro.osito_store.mapper.CartMapper;
import com.lautaro.osito_store.repository.CartRepository;
import com.lautaro.osito_store.repository.UserRepository;
import com.lautaro.osito_store.service.CartService;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartDTO createCart(CartDTO cartDTO) {
        User user = userRepository.findById(cartDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Cart cart = cartMapper.toEntity(cartDTO, user, new ArrayList<>());
        cart.setTotal(0.0);

        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDTO(savedCart);

    }

    @Override 
    public CartDTO getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        return cartMapper.toDTO(cart);
    }

    @Override
    public List<CartDTO> getAllCarts() {
        return cartRepository.findAll().stream()
                .map(cartMapper::toDTO)
                .toList();

    }

    @Override 
    public CartDTO updateCart(Long id, CartDTO cartDTO) {
        Cart existingCart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        if (cartDTO.getUserId() != null && (existingCart.getUser() == null || !existingCart.getUser().getId().equals(cartDTO.getUserId()))) {
            User user = userRepository.findById(cartDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            existingCart.setUser(user);
        }

        existingCart.setTotal(cartDTO.getTotal());

        Cart updatedCart = cartRepository.save(existingCart);
        return cartMapper.toDTO(updatedCart);
    }

    @Override
    public void deleteCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    cartRepository.delete(cart);
    }

    @Override 
    public boolean existsById(Long id) {
        return cartRepository.existsById(id);
    }

@Override
@Transactional
public void updateCartTotal(Cart cart) {
    double total = cart.getItems().stream()
        .mapToDouble(item -> {
            ProductVariant variant = item.getProductVariant();
            Post post = variant != null ? variant.getPost() : null;
            return (post != null ? post.getPrice() * item.getQuantity() : 0.0);
        })
        .sum();

    cart.setTotal(total);
    cartRepository.save(cart);
}


}
