package com.lautaro.osito_store.mapper;

import org.springframework.stereotype.Component;

import com.lautaro.osito_store.dto.ProductVariantDTO;
import com.lautaro.osito_store.entity.Post;
import com.lautaro.osito_store.entity.Product;
import com.lautaro.osito_store.entity.ProductVariant;

@Component
public class ProductVariantMapper {

    public ProductVariantDTO toDTO(ProductVariant variant) {
        if (variant == null) return null;

        ProductVariantDTO dto = new ProductVariantDTO();
        dto.setId(variant.getId());
        dto.setColor(variant.getColor());
        dto.setSize(variant.getSize());
        dto.setProductId(variant.getProduct() != null ? variant.getProduct().getId() : null);
        dto.setPostId(variant.getPost() != null ? variant.getPost().getId() : null);

        return dto;
    }

    public ProductVariant toEntity(ProductVariantDTO dto, Product product, Post post) {
        if (dto == null) return null;

        ProductVariant variant = new ProductVariant();
        variant.setId(dto.getId());
        variant.setColor(dto.getColor());
        variant.setSize(dto.getSize());
        variant.setProduct(product);
        variant.setPost(post);

        return variant;
    }

    public ProductVariant toEntity(ProductVariantDTO dto, Product product) {
    if (dto == null) return null;

    ProductVariant variant = new ProductVariant();
    variant.setId(dto.getId());
    variant.setColor(dto.getColor());
    variant.setSize(dto.getSize());
    variant.setProduct(product);
   
    return variant;
}

}
