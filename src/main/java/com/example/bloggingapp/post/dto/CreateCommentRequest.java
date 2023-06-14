package com.example.bloggingapp.post.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateCommentRequest {

    @NotNull
    private String content;
}
