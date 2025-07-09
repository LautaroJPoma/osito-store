package com.lautaro.osito_store.service;

import java.util.List;
import java.util.Set;

import com.lautaro.osito_store.dto.ProductVariantDTO;
import com.lautaro.osito_store.entity.Product;
import com.lautaro.osito_store.entity.ProductVariant;

public interface ProductVariantService {

    List<ProductVariantDTO> getAllProductVariants();

    ProductVariantDTO getProductVariant(Long id);

    ProductVariantDTO createProductVariant(ProductVariantDTO productVariantDTO);

    Set<ProductVariant> createAndLinkToProduct(List<ProductVariantDTO> variantsDTOs, Product product);

    ProductVariantDTO updateProductVariant(ProductVariantDTO productVariantDTO, Long id);

    void deleteProductVariant(Long id);

    boolean existsById(Long id);
}
