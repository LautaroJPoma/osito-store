package com.lautaro.osito_store.service;



import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.lautaro.osito_store.dto.PostDTO;

public interface PostService {
    Page<PostDTO> getAllPosts(Pageable pageable);

    PostDTO getPostById(Long id);

    PostDTO createPost(PostDTO postDTO);

    PostDTO updatePost(Long id, PostDTO postDTO);

    void deletePost(Long id);
}
