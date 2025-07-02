package com.lautaro.osito_store.service;

import java.util.List;

import com.lautaro.osito_store.dto.ProductDTO;

public interface ProductService {
    
    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);
    
    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO productDTO, Long id);

    void deleteProduct(Long id);
}
