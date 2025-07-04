package com.lautaro.osito_store.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.lautaro.osito_store.dto.PostDTO;
import com.lautaro.osito_store.entity.Post;
import com.lautaro.osito_store.entity.Product;
import com.lautaro.osito_store.entity.ProductVariant;
import com.lautaro.osito_store.entity.User;
import com.lautaro.osito_store.enums.PostStatus;
import com.lautaro.osito_store.mapper.PostMapper;
import com.lautaro.osito_store.mapper.ProductMapper;
import com.lautaro.osito_store.mapper.ProductVariantMapper;
import com.lautaro.osito_store.repository.PostRepository;
import com.lautaro.osito_store.repository.ProductRepository;
import com.lautaro.osito_store.repository.ProductVariantRepository;
import com.lautaro.osito_store.repository.UserRepository;
import com.lautaro.osito_store.service.PostService;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {

    PostRepository postRepository;
    PostMapper postMapper;
    ProductRepository productRepository;
    ProductVariantRepository productVariantRepository;
    ProductVariantMapper productVariantMapper;
    ProductMapper productMapper;
    UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, ProductRepository productRepository,
            ProductVariantRepository productVariantRepository, ProductVariantMapper productVariantMapper,
            ProductMapper productMapper, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
        this.productVariantMapper = productVariantMapper;
        this.productMapper = productMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public PostDTO createPost(PostDTO postDTO) {

        Product product = new Product();
        product.setName(postDTO.getTitle());
        product.setBrand("Desconocida");
        product.setCategory(null);
        product = productRepository.save(product);

        User seller = userRepository.findById(postDTO.getSellerId())
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        Set<ProductVariant> variants = new HashSet<>();
        if (postDTO.getVariantIds() != null) {
            for (Long variantId : postDTO.getVariantIds()) {
                ProductVariant variant = productVariantRepository.findById(variantId)
                        .orElseThrow(() -> new RuntimeException("Variante no encontrada: " + variantId));
                variants.add(variant);
            }
        }

        Post post = postMapper.toEntity(postDTO, product, seller, variants);
        Post savedPost = postRepository.save(post);

        for (ProductVariant variant : variants) {
            variant.setPost(savedPost);
            productVariantRepository.save(variant);
        }
        return postMapper.toDTO(savedPost);
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado: " + id));
        return postMapper.toDTO(post);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toDTO)
                .toList();
    }

    @Transactional
    @Override
    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setPrice(postDTO.getPrice());
        post.setStock(postDTO.getStock());
        post.setImageUrls(postDTO.getImageUrls());
        post.setStatus(PostStatus.valueOf(postDTO.getStatus()));

        post = postRepository.save(post);

        return postMapper.toDTO(post);

    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado: "));
        postRepository.delete(post);
    }

}
