package com.example.bloggingapp.post.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {

    private Integer id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private PostResponseDto postResponseDto;
}
