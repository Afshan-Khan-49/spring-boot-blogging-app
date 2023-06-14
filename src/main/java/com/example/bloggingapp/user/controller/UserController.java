package com.example.bloggingapp.user.controller;

import com.example.bloggingapp.user.dto.UserResponseDto;
import com.example.bloggingapp.user.entity.User;
import com.example.bloggingapp.user.mapper.UserMapper;
import com.example.bloggingapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public String get(@AuthenticationPrincipal OAuth2User user) {
        return "Hello User " + user.getAttribute("name") + " with email id " + user.getAttribute("email");
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.map(user -> ResponseEntity.ok(UserMapper.MAPPER.userToUserResponse(user))).orElseGet(() -> ResponseEntity.unprocessableEntity().build());

    }
}
