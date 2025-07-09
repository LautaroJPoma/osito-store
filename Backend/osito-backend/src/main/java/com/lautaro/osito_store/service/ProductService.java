package com.lautaro.osito_store.service;

import java.util.List;

import com.lautaro.osito_store.dto.PostDTO;
import com.lautaro.osito_store.dto.ProductDTO;
import com.lautaro.osito_store.entity.Category;
import com.lautaro.osito_store.entity.Product;


public interface ProductService {
    
    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);
    
    ProductDTO createProduct(ProductDTO productDTO);

    Product createFromPostDTO(PostDTO postDTO, Category category);

    ProductDTO updateProduct(ProductDTO productDTO, Long id);

    void deleteProduct(Long id);

    boolean existsById(Long id);
}
