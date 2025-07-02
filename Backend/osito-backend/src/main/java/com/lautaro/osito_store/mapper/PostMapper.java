package com.lautaro.osito_store.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.lautaro.osito_store.dto.PostDTO;
import com.lautaro.osito_store.entity.Post;
import com.lautaro.osito_store.entity.Product;
import com.lautaro.osito_store.entity.ProductVariant;
import com.lautaro.osito_store.entity.User;

@Component
public class PostMapper {

    public PostDTO toDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setPrice(post.getPrice());
        dto.setStock(post.getStock());
        dto.setImageUrls(post.getImageUrls());
        dto.setStatus(post.getStatus().toString());
        dto.setProductId(post.getProduct() != null ? post.getProduct().getId() : null);

        List<Long> variantIds = post.getVariants().stream()
            .map(ProductVariant::getId)
            .collect(Collectors.toList());
        dto.setVariantIds(variantIds);

        return dto;
    }

    public Post toEntity(PostDTO dto, Product product, User seller, Set<ProductVariant> variants) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setPrice(dto.getPrice());
        post.setStock(dto.getStock());
        post.setImageUrls(dto.getImageUrls());
        post.setProduct(product);
        post.setSeller(seller);
        post.setVariants(variants);

        return post;
    }
}
