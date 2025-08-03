package com.lautaro.osito_store.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lautaro.osito_store.dto.ProductVariantDTO;
import com.lautaro.osito_store.entity.Post;
import com.lautaro.osito_store.entity.Product;
import com.lautaro.osito_store.entity.ProductVariant;
import com.lautaro.osito_store.mapper.ProductVariantMapper;
import com.lautaro.osito_store.repository.PostRepository;
import com.lautaro.osito_store.repository.ProductRepository;
import com.lautaro.osito_store.repository.ProductVariantRepository;
import com.lautaro.osito_store.service.ProductVariantService;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository variantRepository;
    private final ProductRepository productRepository;
    private final PostRepository postRepository;
    private final ProductVariantMapper variantMapper;

    public ProductVariantServiceImpl(ProductVariantRepository variantRepository,
            ProductRepository productRepository,
            PostRepository postRepository,
            ProductVariantMapper variantMapper) {
        this.variantRepository = variantRepository;
        this.productRepository = productRepository;
        this.postRepository = postRepository;
        this.variantMapper = variantMapper;
    }

    @Override
    public ProductVariantDTO createProductVariant(ProductVariantDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        ProductVariant variant = variantMapper.toEntity(dto, product, post);
        variant = variantRepository.save(variant);

        return variantMapper.toDTO(variant);
    }

    @Override
    public Set<ProductVariant> createAndLinkToProduct(List<ProductVariantDTO> variantDTOs, Product product) {
        Set<ProductVariant> variants = new HashSet<>();

        for (ProductVariantDTO dto : variantDTOs) {
            ProductVariant variant = variantMapper.toEntity(dto, product);
            variants.add(variantRepository.save(variant));
        }

        return variants;
    }

    @Override
    public ProductVariantDTO getProductVariant(Long id) {
        ProductVariant variant = variantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
        return variantMapper.toDTO(variant);
    }

    @Override
    public List<ProductVariantDTO> getAllProductVariants() {
        return variantRepository.findAll().stream()
                .map(variantMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductVariantDTO updateProductVariant(ProductVariantDTO dto, Long id) {
        ProductVariant existing = variantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        existing.setColor(dto.getColor());
        existing.setSize(dto.getSize());
        existing.setProduct(product);
        existing.setPost(post);

        ProductVariant updated = variantRepository.save(existing);
        return variantMapper.toDTO(updated);
    }

    @Override
    public void deleteProductVariant(Long id) {
        if (!variantRepository.existsById(id)) {
            throw new RuntimeException("Variante no encontrada");
        }
        variantRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return variantRepository.existsById(id);
    }

    @Override
    public ProductVariant addImage(Long variantId, String imageUrl) {
    ProductVariant variant = variantRepository.findById(variantId)
        .orElseThrow(() -> new RuntimeException("Variante no encontrada"));

    variant.getImageUrls().add(imageUrl);
    return variantRepository.save(variant);
}

}
