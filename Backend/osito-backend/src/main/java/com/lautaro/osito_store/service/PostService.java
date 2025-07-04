package com.lautaro.osito_store.service;

import java.util.List;

import com.lautaro.osito_store.dto.PostDTO;

public interface PostService {
    
    List<PostDTO> getAllPosts();

    PostDTO getPostById(Long id);

    PostDTO createPost(PostDTO postDTO);

    PostDTO updatePost(Long id, PostDTO postDTO);

    void deletePost(Long id);
}
