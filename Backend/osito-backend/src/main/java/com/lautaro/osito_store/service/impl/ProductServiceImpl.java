package com.lautaro.osito_store.service.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lautaro.osito_store.dto.PostDTO;
import com.lautaro.osito_store.dto.ProductDTO;
import com.lautaro.osito_store.entity.Product;
import com.lautaro.osito_store.entity.ProductVariant;
import com.lautaro.osito_store.mapper.ProductMapper;
import com.lautaro.osito_store.repository.CategoryRepository;
import com.lautaro.osito_store.repository.ProductRepository;
import com.lautaro.osito_store.entity.Category;
import com.lautaro.osito_store.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
            CategoryRepository categoryRepository,
            ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Product product = productMapper.toEntity(dto, category);

        Product saved = productRepository.save(product);

        return productMapper.toDTO(saved);
    }

    @Override
    public Product createFromPostDTO(PostDTO postDTO, Category category) {
        Product product = new Product();
        product.setName(postDTO.getTitle());
        product.setBrand("Desconocida");
        product.setCategory(category);
        return productRepository.save(product);
    }

        

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return productMapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateProduct(ProductDTO dto, Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        existingProduct.setName(dto.getName());
        existingProduct.setBrand(dto.getBrand());
        existingProduct.setCategory(category);

        if (dto.getVariants() != null) {

            existingProduct.getVariants().clear();

            List<ProductVariant> updatedVariants = dto.getVariants().stream().map(vdto -> {
                ProductVariant variant = new ProductVariant();
                variant.setColor(vdto.getColor());
                variant.setSize(vdto.getSize());
                variant.setProduct(existingProduct);
                return variant;
            }).collect(Collectors.toList());

            existingProduct.getVariants().addAll(updatedVariants);
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }
}
