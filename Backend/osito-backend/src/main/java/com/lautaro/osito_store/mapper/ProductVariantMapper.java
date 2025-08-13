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
        dto.setColorHex(variant.getColorHex());
        dto.setSize(variant.getSize());
        dto.setProductId(variant.getProduct() != null ? variant.getProduct().getId() : null);
        dto.setPostId(variant.getPost() != null ? variant.getPost().getId() : null);
        dto.setImageUrls(variant.getImageUrls());
        dto.setStock(variant.getStock());

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
        variant.setImageUrls(dto.getImageUrls());
        variant.setColorHex(dto.getColorHex());
        variant.setStock(dto.getStock());

        return variant;
    }

    public ProductVariant toEntity(ProductVariantDTO dto, Product product) {
    if (dto == null) return null;

    ProductVariant variant = new ProductVariant();
    variant.setId(dto.getId());
    variant.setColor(dto.getColor());
    variant.setColorHex(dto.getColorHex());
    variant.setSize(dto.getSize());
    variant.setProduct(product);
    variant.setImageUrls(dto.getImageUrls());
    variant.setStock(dto.getStock());
    
   
    return variant;
}

}
