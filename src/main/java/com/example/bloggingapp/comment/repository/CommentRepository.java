package com.example.bloggingapp.comment.repository;

import com.example.bloggingapp.comment.entity.Comment;
import com.example.bloggingapp.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Page<Comment> findByPostOrderByCreatedAtDesc(Post post, Pageable pageable);
}
