package com.lautaro.osito_store.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
import com.lautaro.osito_store.service.StockService;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository variantRepository;
    private final ProductRepository productRepository;
    private final PostRepository postRepository;
    private final ProductVariantMapper variantMapper;
   
    private final StockService stockService;
   

    public ProductVariantServiceImpl(ProductVariantRepository variantRepository,
            ProductRepository productRepository,
            PostRepository postRepository,
            ProductVariantMapper variantMapper,
            StockService stockService
            ) {
        this.variantRepository = variantRepository;
        this.productRepository = productRepository;
        this.postRepository = postRepository;
        this.variantMapper = variantMapper;
        this.stockService = stockService;
        
    }

 @Override
public ProductVariantDTO createProductVariant(ProductVariantDTO dto) {
    Product product = productRepository.findById(dto.getProductId())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

    Post post = postRepository.findById(dto.getPostId())
            .orElseThrow(() -> new RuntimeException("Post no encontrado"));

    ProductVariant variant = variantMapper.toEntity(dto, product, post);

    // Si hay variantes existentes con mismo color y colorHex, copiar sus imageUrls
    Optional<ProductVariant> existingVariantOpt = post.getVariants().stream()
        .filter(v -> v.getColor().equalsIgnoreCase(dto.getColor()) &&
                     v.getColorHex().equalsIgnoreCase(dto.getColorHex()))
        .findFirst();

    if (existingVariantOpt.isPresent()) {
        variant.setImageUrls(new ArrayList<>(existingVariantOpt.get().getImageUrls()));
    } else {
        // Si no existe, dejar la lista vacía o setear las URLs que traiga el DTO (si las tiene)
        variant.setImageUrls(dto.getImageUrls() != null ? dto.getImageUrls() : new ArrayList<>());
    }

    variant = variantRepository.save(variant);

    stockService.updatePostStock(post.getId());

    return variantMapper.toDTO(variant);
}



    @Override
    public Set<ProductVariant> createAndLinkToProduct(List<ProductVariantDTO> variantDTOs, Product product) {
        Set<ProductVariant> variants = new HashSet<>();

        for (ProductVariantDTO dto : variantDTOs) {
            ProductVariant variant = variantMapper.toEntity(dto, product);
            variants.add(variant);
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
        existing.setColorHex(dto.getColorHex());
        existing.setSize(dto.getSize());
        existing.setProduct(product);
        existing.setPost(post);

        ProductVariant updated = variantRepository.save(existing);
        stockService.updatePostStock(updated.getPost().getId());
        return variantMapper.toDTO(updated);
    }

    @Override
public void deleteProductVariant(Long id) {
    ProductVariant variant = variantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
    Long postId = variant.getPost().getId();

    variantRepository.deleteById(id);
    stockService.updatePostStock(postId);
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
