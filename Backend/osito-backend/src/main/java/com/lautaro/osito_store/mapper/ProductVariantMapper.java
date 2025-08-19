package com.lautaro.osito_store.mapper;

import java.util.ArrayList;

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
        // Clonamos la lista al pasar al DTO
        dto.setImageUrls(variant.getImageUrls() != null ? new ArrayList<>(variant.getImageUrls()) : new ArrayList<>());
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
        variant.setColorHex(dto.getColorHex());
        variant.setStock(dto.getStock());
        // Clonamos la lista al crear la entidad
        // NO copiar imágenes de variantes existentes
variant.setImageUrls(dto.getImageUrls() != null ? new ArrayList<>(dto.getImageUrls()) : new ArrayList<>());


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
        variant.setStock(dto.getStock());
        // Clonamos la lista
       // NO copiar imágenes de variantes existentes
variant.setImageUrls(dto.getImageUrls() != null ? new ArrayList<>(dto.getImageUrls()) : new ArrayList<>());


        return variant;
    }
}


