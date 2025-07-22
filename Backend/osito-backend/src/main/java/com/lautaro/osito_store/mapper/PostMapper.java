package com.lautaro.osito_store.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lautaro.osito_store.dto.PostDTO;
import com.lautaro.osito_store.dto.ProductVariantDTO;
import com.lautaro.osito_store.entity.Category;
import com.lautaro.osito_store.entity.Post;
import com.lautaro.osito_store.entity.Product;
import com.lautaro.osito_store.entity.ProductVariant;
import com.lautaro.osito_store.entity.User;

@Component
public class PostMapper {

    @Autowired
    private ProductVariantMapper productVariantMapper;

   public PostDTO toDTO(Post post) {
    PostDTO dto = new PostDTO();
    dto.setId(post.getId());
    dto.setTitle(post.getTitle());
    dto.setDescription(post.getDescription());
    dto.setPrice(post.getPrice());
    dto.setStock(post.getStock());
    dto.setImageUrls(post.getImageUrls());
    dto.setStatus(post.getStatus() != null ? post.getStatus().toString() : null);
    dto.setProductId(post.getProduct() != null ? post.getProduct().getId() : null);
    dto.setCategoryId(post.getCategory() != null ? post.getCategory().getId() : null);
    dto.setSellerId(post.getSeller() != null ? post.getSeller().getId() : null);

    List<ProductVariantDTO> variants = post.getVariants() != null
        ? post.getVariants().stream()
            .map(productVariantMapper::toDTO)
            .collect(Collectors.toList())
        : Collections.emptyList();
    dto.setVariants(variants);

    return dto;
}

public Post toEntity(PostDTO dto, Product product, Category category, User seller, Set<ProductVariant> variants) {
    Post post = new Post();
    post.setTitle(dto.getTitle());
    post.setDescription(dto.getDescription());
    post.setPrice(dto.getPrice());
    post.setStock(dto.getStock());
    post.setImageUrls(dto.getImageUrls());
    post.setProduct(product);
    post.setCategory(category);
    post.setSeller(seller);
    post.setVariants(variants);

    return post;
}

}