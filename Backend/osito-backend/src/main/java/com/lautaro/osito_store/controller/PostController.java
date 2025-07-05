package com.lautaro.osito_store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lautaro.osito_store.dto.PostDTO;
import com.lautaro.osito_store.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        if (!postService.existsById(id)) {
            throw new RuntimeException("Post con ID " + id + " no existe");
        }
        PostDTO post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        if (postDTO.getTitle() == null || postDTO.getTitle().isEmpty()) {
            throw new RuntimeException("El título no puede estar vacío");
        }
        if (postDTO.getDescription() == null || postDTO.getDescription().isEmpty()) {
            throw new RuntimeException("La descripción no puede estar vacía");
        }

        PostDTO newPost = postService.createPost(postDTO);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        if (!postService.existsById(id)) {
            throw new RuntimeException("Post con ID " + id + " no encontrado");
        }

        PostDTO updated = postService.updatePost(id, postDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (!postService.existsById(id)) {
            throw new RuntimeException("Post con ID " + id + " no encontrado");
        }
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

}
