package com.lautaro.osito_store.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.lautaro.osito_store.dto.PostDTO;
import com.lautaro.osito_store.dto.ProductVariantDTO;
import com.lautaro.osito_store.entity.Category;
import com.lautaro.osito_store.entity.Post;
import com.lautaro.osito_store.entity.Product;
import com.lautaro.osito_store.entity.ProductVariant;
import com.lautaro.osito_store.entity.User;
import com.lautaro.osito_store.enums.PostStatus;
import com.lautaro.osito_store.mapper.PostMapper;
import com.lautaro.osito_store.mapper.ProductVariantMapper;
import com.lautaro.osito_store.repository.CategoryRepository;
import com.lautaro.osito_store.repository.PostRepository;
import com.lautaro.osito_store.repository.ProductRepository;
import com.lautaro.osito_store.repository.ProductVariantRepository;
import com.lautaro.osito_store.repository.UserRepository;
import com.lautaro.osito_store.service.PostService;
import com.lautaro.osito_store.service.ProductService;
import com.lautaro.osito_store.service.ProductVariantService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final PostMapper postMapper;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ProductVariantRepository productVariantRepository;
    private final ProductVariantService productVariantService;
    private final ProductVariantMapper variantMapper;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, ProductRepository productRepository,
            CategoryRepository categoryRepository,
            ProductVariantRepository productVariantRepository,
            ProductService productService, ProductVariantService productVariantService,
            ProductVariantMapper variantMapper, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productVariantRepository = productVariantRepository;
        this.productService = productService;
        this.productVariantService = productVariantService;
        this.variantMapper = variantMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public PostDTO createPost(PostDTO postDTO) {

        Objects.requireNonNull(postDTO.getCategoryId(), "Category ID no puede ser null");
        
        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + postDTO.getCategoryId()));

        
        //User seller = userRepository.findById(postDTO.getSellerId())
               // .orElseThrow(() -> new RuntimeException("Vendedor no encontrado con ID: " + postDTO.getSellerId()));
        User seller;
    if (postDTO.getSellerId() != null) {
        seller = userRepository.findById(postDTO.getSellerId())
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado con ID: " + postDTO.getSellerId()));
    } else {
        Long defaultSellerId = 1L; 
        seller = userRepository.findById(defaultSellerId)
                .orElseThrow(() -> new RuntimeException("Seller default no encontrado"));
    }

        
        Product product = productService.createFromPostDTO(postDTO, category);

       
        Set<ProductVariant> variants = postDTO.getVariants() != null
                ? productVariantService.createAndLinkToProduct(postDTO.getVariants(), product)
                : new HashSet<>();

        
        Post post = postMapper.toEntity(postDTO, product, category, seller, variants);
        Post savedPost = postRepository.save(post);

        
        variants.forEach(variant -> {
            variant.setPost(savedPost);
            productVariantRepository.save(variant);
        });

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

        if (postDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(postDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            post.setCategory(category);

            if (post.getProduct() != null) {
                post.getProduct().setCategory(category);
                productRepository.save(post.getProduct());
            }
        }
        // Manejo de variantes
        if (postDTO.getVariants() != null) {
            // Primero desvincular las variantes antiguas
            for (ProductVariant oldVariant : post.getVariants()) {
                oldVariant.setPost(null);
                productVariantRepository.save(oldVariant);
            }
            post.getVariants().clear();
            postRepository.save(post); // Guardar cambios antes de añadir nuevas

            Set<ProductVariant> newVariants = new HashSet<>();

            // Procesar variantes como objetos completos
            for (ProductVariantDTO variantDTO : postDTO.getVariants()) {
                ProductVariant newVariant = variantMapper.toEntity(variantDTO, post.getProduct(), post);
                newVariant = productVariantRepository.save(newVariant);
                newVariants.add(newVariant);
            }

            // Asignar nuevas variantes al post
            post.getVariants().addAll(newVariants);
        }

        // Actualizar campos normales
        if (postDTO.getTitle() != null) {
            post.setTitle(postDTO.getTitle());
        }
        if (postDTO.getDescription() != null) {
            post.setDescription(postDTO.getDescription());
        }
        if (postDTO.getPrice() != null) {
            post.setPrice(postDTO.getPrice());
        }
        if (postDTO.getStock() != null) {
            post.setStock(postDTO.getStock());
        }
        if (postDTO.getImageUrls() != null) {
            post.setImageUrls(postDTO.getImageUrls());
        }
        if (postDTO.getStatus() != null) {
            post.setStatus(PostStatus.valueOf(postDTO.getStatus()));
        }

        Post updatedPost = postRepository.save(post);

        return postMapper.toDTO(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado: "));
        postRepository.delete(post);
    }

    @Override
    public boolean existsById(Long id) {
        return postRepository.existsById(id);
    }

}
