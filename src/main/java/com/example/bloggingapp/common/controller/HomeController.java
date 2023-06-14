package com.example.bloggingapp.common.controller;

import com.example.bloggingapp.common.util.LoginUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String get() {

        return "Hello User with email id " + LoginUtils.getCurrentUserEmail();
    }
}
