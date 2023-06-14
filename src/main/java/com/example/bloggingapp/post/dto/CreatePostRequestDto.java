package com.example.bloggingapp.post.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CreatePostRequestDto {

    @NotNull
    private String title;
    private String description;
    @NotNull
    private String body;
    private List<String> tagList;
    @NotNull
    private String userEmail;
}
