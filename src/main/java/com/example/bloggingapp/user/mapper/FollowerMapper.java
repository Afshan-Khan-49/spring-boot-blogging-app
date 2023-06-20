package com.example.bloggingapp.user.mapper;

import com.example.bloggingapp.user.dto.FollowerResponseDto;
import com.example.bloggingapp.user.entity.User;

public interface FollowerMapper {

    FollowerResponseDto userToFollowerResponseDto(User targetUser, User sourceUser);
}
