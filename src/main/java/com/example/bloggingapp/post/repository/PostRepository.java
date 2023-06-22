package com.example.bloggingapp.post.repository;

import com.example.bloggingapp.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    boolean existsByTitle(String title);

    Optional<Post> findByTitle(String title);

}
