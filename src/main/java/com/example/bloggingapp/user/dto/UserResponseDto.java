package com.example.bloggingapp.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String firstName;
    private String lastName;
    private String email;

}
