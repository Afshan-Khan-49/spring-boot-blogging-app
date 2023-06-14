package com.example.bloggingapp.post.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UpdatePostRequestDto {

    @NotNull
    private String title;
    private String description;
    @NotNull
    private String body;
    private List<String> tags;
}
