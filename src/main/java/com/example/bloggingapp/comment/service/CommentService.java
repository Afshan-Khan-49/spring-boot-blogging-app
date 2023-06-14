package com.example.bloggingapp.comment.service;

import com.example.bloggingapp.post.dto.CommentResponse;
import com.example.bloggingapp.post.entity.Post;
import com.example.bloggingapp.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentResponse createComment(Post post, User user, String content);

    Page<CommentResponse> getComments(Post post, Pageable pageable);

    void deleteComment(int commentId);
}
