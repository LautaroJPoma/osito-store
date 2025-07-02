package com.lautaro.osito_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lautaro.osito_store.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
