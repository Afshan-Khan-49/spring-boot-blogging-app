package com.example.bloggingapp.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FollowerResponseDto extends UserResponseDto {

    private boolean isFollowing;

    public FollowerResponseDto(String firstName, String lastName, String email, boolean isFollowing) {
        super(firstName, lastName, email);
        this.isFollowing = isFollowing;
    }
}
