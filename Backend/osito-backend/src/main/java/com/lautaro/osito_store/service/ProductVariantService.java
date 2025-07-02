package com.lautaro.osito_store.service;

import java.util.List;

import com.lautaro.osito_store.dto.ProductVariantDTO;

public interface ProductVariantService {

    List<ProductVariantDTO> getAllProductVariants();

    ProductVariantDTO getProductVariant(Long id);

    ProductVariantDTO createProductVariant(ProductVariantDTO productVariantDTO);

    ProductVariantDTO updateProductVariant(ProductVariantDTO productVariantDTO, Long id);

    void deleteProductVariant(Long id);
}
