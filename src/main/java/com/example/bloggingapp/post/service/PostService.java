package com.example.bloggingapp.post.service;

import com.example.bloggingapp.post.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    PostResponseDto createPost(CreatePostRequestDto createPostRequestDto);

    PostResponseDto getPostByTitle(String title);

    PostResponseDto updatePost(String title, UpdatePostRequestDto updatePostRequestDto);

    void deletePost(String title);

    CommentResponse addComment(String title, String content);

    Page<CommentResponse> getComments(String title, Pageable pageable);

    void deleteComment(int commentId);

    PostResponseDto favoritePost(String title);

    PostResponseDto unfavorite(String title);

    ListPostResponse getPosts(String authorEmail, List<String> tags, boolean isFavorite, Pageable pageable);
}
