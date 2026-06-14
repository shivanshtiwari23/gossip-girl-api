package com.gossipgirl.api.repository;

import com.gossipgirl.api.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPostIdOrderByCreatedAtDesc(Long postId, Pageable pageable);
    void deleteByPostId(Long postId);
}