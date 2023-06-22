package com.example.bloggingapp.user.service.impls;

import com.example.bloggingapp.common.util.LoginUtils;
import com.example.bloggingapp.user.dto.FollowerResponseDto;
import com.example.bloggingapp.user.dto.UserResponseDto;
import com.example.bloggingapp.user.entity.User;
import com.example.bloggingapp.user.exception.UserNotFoundException;
import com.example.bloggingapp.user.mapper.FollowerMapper;
import com.example.bloggingapp.user.mapper.UserMapper;
import com.example.bloggingapp.user.repository.UserRepository;
import com.example.bloggingapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FollowerMapper followerMapper;

    @Override
    public FollowerResponseDto followUser(String email) {
        User targetUser = checkIfUserExists(email);
        User sourceUser = checkIfUserExists(LoginUtils.getCurrentUserEmail());
        sourceUser.follow(targetUser);
        return followerMapper.userToFollowerResponseDto(targetUser, sourceUser);
    }

    @Override
    public FollowerResponseDto unFollow(String email) {
        User targetUser = checkIfUserExists(email);
        User sourceUser = checkIfUserExists(LoginUtils.getCurrentUserEmail());
        sourceUser.unfollow(targetUser);
        return followerMapper.userToFollowerResponseDto(targetUser, sourceUser);
    }

    @Override
    public Page<UserResponseDto> getFollowers(Pageable pageRequest) {
        String currentUserEmail = LoginUtils.getCurrentUserEmail();
        checkIfUserExists(currentUserEmail);
        return userRepository.getFollowers(currentUserEmail, pageRequest).map(UserMapper.MAPPER::userToUserResponse);
    }

    @Override
    public Page<UserResponseDto> getFollowing(Pageable pageRequest) {
        String currentUserEmail = LoginUtils.getCurrentUserEmail();
        checkIfUserExists(currentUserEmail);
        return userRepository.getFollowing(currentUserEmail, pageRequest).map(UserMapper.MAPPER::userToUserResponse);
    }

    private User checkIfUserExists(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Could not find user with email:" + LoginUtils.getCurrentUserEmail()));
    }
}
