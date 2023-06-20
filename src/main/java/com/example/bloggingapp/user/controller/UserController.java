package com.example.bloggingapp.user.controller;

import com.example.bloggingapp.user.dto.FollowerResponseDto;
import com.example.bloggingapp.user.dto.UserResponseDto;
import com.example.bloggingapp.user.entity.User;
import com.example.bloggingapp.user.mapper.UserMapper;
import com.example.bloggingapp.user.repository.UserRepository;
import com.example.bloggingapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.map(user -> ResponseEntity.ok(UserMapper.MAPPER.userToUserResponse(user))).orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }

    @PostMapping("/{email}/follow")
    public FollowerResponseDto followUser(@PathVariable String email) {
        return userService.followUser(email);
    }

    @DeleteMapping("/{email}/follow")
    public FollowerResponseDto unFollowUser(@PathVariable String email) {
        return userService.unFollow(email);
    }

    @GetMapping("/followers")
    public Page<UserResponseDto> getFollowers(Pageable pageRequest) {
        return userService.getFollowers(pageRequest);
    }

    @GetMapping("/following")
    public Page<UserResponseDto> getFollowing(Pageable pageRequest) {
        return userService.getFollowing(pageRequest);
    }
}
