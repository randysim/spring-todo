package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.security.GoogleOAuth2User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path="/{userId}")
    public User getUser(@PathVariable("userId") Long id) {
        return userService.getUser(id);
    }

    @GetMapping(path = "/authenticated")
    public User getAuthenticatedUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) return null;

        GoogleOAuth2User googleOAuth2User = (GoogleOAuth2User) principal;
        return userService.getAuthenticatedUser(googleOAuth2User);
    }
}
