package com.gossipgirl.api.service;

import com.gossipgirl.api.model.Comment;
import com.gossipgirl.api.model.Post;
import com.gossipgirl.api.repository.CommentRepository;
import com.gossipgirl.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Page<Post> getFeed(String sort, String city, int page) {
        PageRequest pageable = PageRequest.of(page, 10);

        if (city != null && !city.isBlank()) {
            return sort.equals("hot")
                    ? postRepository.findByCityOrderByCommentCountDesc(city, pageable)
                    : postRepository.findByCityOrderByCreatedAtDesc(city, pageable);
        }

        return sort.equals("hot")
                ? postRepository.findAllByOrderByCommentCountDesc(pageable)
                : postRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Comment addComment(Long postId, String text) {
        Post post = getPostById(postId);
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setText(text);
        post.setCommentCount(post.getCommentCount() + 1);
        postRepository.save(post);
        return commentRepository.save(comment);
    }

    public Page<Comment> getComments(Long postId, int page) {
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId, PageRequest.of(page, 20));
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        commentRepository.deleteByPostId(id);
        postRepository.delete(post);
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        Post post = comment.getPost();
        post.setCommentCount(Math.max(0, post.getCommentCount() - 1));
        postRepository.save(post);
        commentRepository.delete(comment);
    }
}