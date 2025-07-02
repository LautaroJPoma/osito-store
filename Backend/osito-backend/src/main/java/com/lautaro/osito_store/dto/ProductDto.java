package com.lautaro.osito_store.dto;

import java.util.List;

public class ProductDTO {
    private Long id;

    private String name;

    private String brand;

    private Long categoryId;

    private List<ProductVariantDTO> variants;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String brand, Long categoryId, List<ProductVariantDTO> variants) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.categoryId = categoryId;
        this.variants = variants;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<ProductVariantDTO> getVariants() {
        return variants;
    }

    public void setVariants(List<ProductVariantDTO> variants) {
        this.variants = variants;
    }

}
