package com.example.bloggingapp.user.mapper;

import com.example.bloggingapp.user.dto.FollowerResponseDto;
import com.example.bloggingapp.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class FollowerMapperImpl implements FollowerMapper {
    @Override
    public FollowerResponseDto userToFollowerResponseDto(User targetUser, User sourceUser) {
        return new FollowerResponseDto(targetUser.getFirstName(), targetUser.getLastName(), targetUser.getEmail(), sourceUser.isAlreadyFollowing(targetUser));
    }
}
