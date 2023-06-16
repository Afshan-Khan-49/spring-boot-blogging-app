package com.example.bloggingapp.post.service;

import com.example.bloggingapp.post.dto.CommentResponse;
import com.example.bloggingapp.post.dto.CreatePostRequestDto;
import com.example.bloggingapp.post.dto.PostResponseDto;
import com.example.bloggingapp.post.dto.UpdatePostRequestDto;
import com.example.bloggingapp.post.entity.Post;
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

    List<PostResponseDto> getPosts(String authorEmail, List<String> tags, boolean isFavorite);
}
