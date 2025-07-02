package com.lautaro.osito_store.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lautaro.osito_store.dto.CategoryDTO;
import com.lautaro.osito_store.entity.Category;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());

        if (category.getProducts() != null) {
            List<Long> productIds = category.getProducts()
                    .stream()
                    .map(product -> product
                            .getId())
                    .toList();
            categoryDto.setProductIds(productIds);
        } else {
            categoryDto.setProductIds(new ArrayList<>());
        }
        return categoryDto;
    }

    public Category toEntity(CategoryDTO categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }

}
