package com.example.bloggingapp.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;

@UtilityClass
public class LoginUtils {

    public static OAuth2User getCurrentUser() {
        return (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getCurrentUserEmail() {
        return getCurrentUser().getAttribute("email");
    }
}
