package com.example.backend.controller;

import com.example.backend.dto.inbound.TodoListRequestDTO;
import com.example.backend.dto.outbound.TodoListResponseDTO;
import com.example.backend.model.TodoList;
import com.example.backend.security.GoogleOAuth2User;
import com.example.backend.service.TodoListService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/todolist")
public class TodoListController {
    private final TodoListService todoListService;

    @Autowired
    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @PostMapping
    @Transactional
    public TodoListResponseDTO createTodoList(@AuthenticationPrincipal GoogleOAuth2User principal, @RequestBody TodoListRequestDTO requestDTO) {
        return todoListService.createTodoList(requestDTO.getTitle(), principal.getEmail());
    }

    @DeleteMapping(path = "/${todoListId}")
    @Transactional
    public void deleteTodoList(@AuthenticationPrincipal GoogleOAuth2User principal, Long todoListId) {
        
    }

    @PutMapping(path = "/${todoListId}")
    @Transactional
    public TodoListResponseDTO updateTodoList(@AuthenticationPrincipal GoogleOAuth2User principal, @PathVariable Long todoListId, @RequestBody TodoListRequestDTO requestDTO) {

    }
}
