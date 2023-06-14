package com.example.bloggingapp.post.repository;

import com.example.bloggingapp.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Integer> {
    boolean existsByTitle(String title);
    Page<Post> findByAuthor_Email(String email, Pageable pageable);

    Optional<Post> findByTitle(String title);

    Optional<Post> findByTitleAndAuthor_Email(String title, String currentUserEmail);
}
