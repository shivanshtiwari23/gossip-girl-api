package com.gossipgirl.api.repository;

import com.gossipgirl.api.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Post> findAllByOrderByCommentCountDesc(Pageable pageable);

    Page<Post> findByCityOrderByCreatedAtDesc(String city, Pageable pageable);

    Page<Post> findByCityOrderByCommentCountDesc(String city, Pageable pageable);
}