package com.example.backend.controller;

import com.example.backend.dto.outbound.TodoListResponseDTO;
import com.example.backend.model.User;
import com.example.backend.security.GoogleOAuth2User;
import com.example.backend.service.TodoListService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    private final UserService userService;
    private final TodoListService todoListService;

    @Autowired
    public UserController(UserService userService, TodoListService todoListService) {
        this.userService = userService;
        this.todoListService = todoListService;
    }

    @GetMapping(path="/{userId}")
    public User getUser(@PathVariable("userId") Long id) {
        return userService.getUser(id);
    }

    @GetMapping(path="/todolist")
    public List<TodoListResponseDTO> getUserTodoLists(@AuthenticationPrincipal GoogleOAuth2User principal) {
        return todoListService.getUserTodoLists(principal.getEmail());
    }

    @GetMapping(path = "/authenticated")
    public User getAuthenticatedUser(@AuthenticationPrincipal GoogleOAuth2User principal) {
        return userService.getAuthenticatedUser(principal);
    }
}
