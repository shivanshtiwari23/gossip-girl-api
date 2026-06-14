package com.gossipgirl.api.controller;

import com.gossipgirl.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PostService postService;

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted. XOXO");
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        postService.deleteComment(id);
        return ResponseEntity.ok("Comment deleted. XOXO");
    }
}