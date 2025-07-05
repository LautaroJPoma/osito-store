package com.lautaro.osito_store.service;

import java.util.List;

import com.lautaro.osito_store.dto.CategoryDTO;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategoryById(Long id);

    List<CategoryDTO> getAllCategories();

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);

    boolean existsById(Long id);

}
