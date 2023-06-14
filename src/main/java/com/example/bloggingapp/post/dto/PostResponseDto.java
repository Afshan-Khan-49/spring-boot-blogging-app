package com.example.bloggingapp.post.dto;

import com.example.bloggingapp.user.dto.UserResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDto {

    private String description;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<String> tags;
    private boolean isFavorite;

    private UserResponseDto userResponseDto;
}
