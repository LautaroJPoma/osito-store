package com.lautaro.osito_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lautaro.osito_store.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
