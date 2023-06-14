package com.example.bloggingapp.comment.mapper;

import com.example.bloggingapp.comment.entity.Comment;
import com.example.bloggingapp.post.dto.CommentResponse;
import com.example.bloggingapp.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentMapperImpl implements CommentMapper {
    private final PostMapper postMapper;
    @Override
    public CommentResponse commentToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .postResponseDto(postMapper.postToPostResponseDto(comment.getPost()))
                .build();
    }
}
