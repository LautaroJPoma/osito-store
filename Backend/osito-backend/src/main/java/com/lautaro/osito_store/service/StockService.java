package com.lautaro.osito_store.service;

import org.springframework.stereotype.Service;

import com.lautaro.osito_store.entity.Post;
import com.lautaro.osito_store.repository.PostRepository;

@Service
public class StockService {
    private final PostRepository postRepository;

    public StockService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void updatePostStock(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post no encontrado: " + postId));
        int totalStock = post.getVariants().stream()
                .mapToInt(v -> v.getStock().intValue())
                .sum();
        post.setStock(totalStock);
        postRepository.save(post);
    }
}
