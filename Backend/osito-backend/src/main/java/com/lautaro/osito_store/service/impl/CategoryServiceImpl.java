package com.lautaro.osito_store.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lautaro.osito_store.dto.CategoryDTO;
import com.lautaro.osito_store.entity.Category;
import com.lautaro.osito_store.mapper.CategoryMapper;
import com.lautaro.osito_store.repository.CategoryRepository;
import com.lautaro.osito_store.repository.ProductRepository;
import com.lautaro.osito_store.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    public final CategoryRepository categoryRepository;
    public final CategoryMapper categoryMapper;
    public final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper,
            ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.productRepository = productRepository;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        return categoryMapper.toDTO(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        existingCategory.setName(categoryDTO.getName());
        existingCategory.setDescription(categoryDTO.getDescription());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada");
        }

        categoryRepository.deleteById(id);
    }

}
