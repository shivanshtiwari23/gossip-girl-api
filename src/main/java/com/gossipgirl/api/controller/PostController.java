package com.gossipgirl.api.controller;

import com.gossipgirl.api.model.Comment;
import com.gossipgirl.api.model.Post;
import com.gossipgirl.api.model.Post.PostType;
import com.gossipgirl.api.service.CloudinaryService;
import com.gossipgirl.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CloudinaryService cloudinaryService;

    @GetMapping("/feed")
    public ResponseEntity<Page<Post>> getFeed(
            @RequestHeader(value = "X-Sort", defaultValue = "new") String sort,
            @RequestHeader(value = "X-City", required = false) String city,
            @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(postService.getFeed(sort, city, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        return ResponseEntity.ok(postService.createPost(post));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Page<Comment>> getComments(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(postService.getComments(id, page));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComment(
            @PathVariable Long id,
            @RequestBody CommentRequest request
    ) {
        return ResponseEntity.ok(postService.addComment(id, request.text()));
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<Post> uploadPost(
            @RequestParam("image") MultipartFile image,
            @RequestParam("headline") String headline,
            @RequestParam("gossipText") String gossipText,
            @RequestParam("postType") String postType,
            @RequestParam(value = "city", required = false) String city
    ) throws IOException {
        String photoUrl = cloudinaryService.uploadImage(image);
        Post post = new Post();
        post.setHeadline(headline);
        post.setGossipText(gossipText);
        post.setPhotoUrl(photoUrl);
        post.setPostType(PostType.valueOf(postType.toUpperCase()));
        post.setCity(city);
        return ResponseEntity.ok(postService.createPost(post));
    }

    record CommentRequest(String text) {}
}