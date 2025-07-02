package com.lautaro.osito_store.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.lautaro.osito_store.dto.ProductDTO;
import com.lautaro.osito_store.dto.ProductVariantDTO;
import com.lautaro.osito_store.entity.Category;
import com.lautaro.osito_store.entity.Product;
import com.lautaro.osito_store.entity.ProductVariant;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        if (product == null) return null;

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);

        List<ProductVariantDTO> variantDTOs = product.getVariants().stream().map(variant -> {
            ProductVariantDTO vdto = new ProductVariantDTO();
            vdto.setId(variant.getId());
            vdto.setColor(variant.getColor());
            vdto.setSize(variant.getSize());
            return vdto;
        }).collect(Collectors.toList());

        dto.setVariants(variantDTOs);
        return dto;
    }

    public Product toEntity(ProductDTO dto, Category category) {
        if (dto == null) return null;

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setCategory(category);

        if (dto.getVariants() != null) {
            List<ProductVariant> variants = dto.getVariants().stream().map(vdto -> {
                ProductVariant variant = new ProductVariant();
                variant.setColor(vdto.getColor());
                variant.setSize(vdto.getSize());
                variant.setProduct(product); 
                return variant;
            }).collect(Collectors.toList());
            product.setVariants(variants);
        }

        return product;
    }
}
