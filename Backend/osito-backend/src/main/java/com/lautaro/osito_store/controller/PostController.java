package com.lautaro.osito_store.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lautaro.osito_store.dto.PostDTO;
import com.lautaro.osito_store.entity.Post;
import com.lautaro.osito_store.service.CategoryService;
import com.lautaro.osito_store.service.CloudinaryService;
import com.lautaro.osito_store.service.PostService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CloudinaryService cloudinaryService;

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
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDTO postDTO) {
        try {
           
            if (postDTO.getCategoryId() == null || !categoryService.existsById(postDTO.getCategoryId())) {
                return ResponseEntity.badRequest().body(
                    postDTO.getCategoryId() == null ? 
                    "Se requiere ID de categoría" : 
                    "Categoría no encontrada con ID: " + postDTO.getCategoryId()
                );
            }

            PostDTO newPost = postService.createPost(postDTO);
            return new ResponseEntity<>(newPost, HttpStatus.CREATED);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error interno: " + e.getMessage());
        }
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

   @PostMapping("/{id}/images")
public ResponseEntity<Post> uploadPostImage(
        @PathVariable Long id,
        @RequestParam("file") MultipartFile file) {
    try {
        String imageUrl = cloudinaryService.uploadImage(file);
        Post updatedPost = postService.addImage(id, imageUrl);
        return ResponseEntity.ok(updatedPost);
    } catch (IOException e) {
        throw new RuntimeException("Error al subir la imagen a Cloudinary", e);
    }
}

}
