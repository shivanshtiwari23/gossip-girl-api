package com.gossipgirl.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String headline;

    @Column(nullable =false ,columnDefinition = "TEXT")
    private String gossipText;

    @Column(nullable = false,length = 500)
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostType postType;

    private String city;

    @Column(nullable = false)
    private int commentCount = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum PostType {
        GOSSIP, PARTY
    }
}
