package com.example.bloggingapp.post.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class CreatePostRequestDto {

    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String body;
    private List<String> tagList;
    @Email
    private String userEmail;

}
