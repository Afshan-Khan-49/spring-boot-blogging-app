package com.example.bloggingapp.comment.mapper;

import com.example.bloggingapp.comment.entity.Comment;
import com.example.bloggingapp.post.dto.CommentResponse;

public interface CommentMapper {

    CommentResponse commentToCommentResponse(Comment comment);
}
