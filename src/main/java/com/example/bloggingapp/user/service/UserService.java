package com.example.bloggingapp.user.service;

import com.example.bloggingapp.user.dto.FollowerResponseDto;
import com.example.bloggingapp.user.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    FollowerResponseDto followUser(String email);

    FollowerResponseDto unFollow(String email);

    Page<UserResponseDto> getFollowers(Pageable pageRequest);

    Page<UserResponseDto> getFollowing(Pageable pageRequest);
}
