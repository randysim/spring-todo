package com.example.backend.controller;

import com.example.backend.dto.inbound.TodoListRequestDTO;
import com.example.backend.dto.outbound.TodoListResponseDTO;
import com.example.backend.security.GoogleOAuth2User;
import com.example.backend.service.TodoListService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/todolists")
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

    @DeleteMapping(path = "/{todoListId}")
    @Transactional
    public void deleteTodoList(@AuthenticationPrincipal GoogleOAuth2User principal, @PathVariable("todoListId") Long todoListId) {
        todoListService.deleteTodoList(todoListId, principal.getEmail());
    }

    @PutMapping(path = "/{todoListId}")
    @Transactional
    public TodoListResponseDTO updateTodoList(@AuthenticationPrincipal GoogleOAuth2User principal, @PathVariable("todoListId") Long todoListId, @RequestBody TodoListRequestDTO requestDTO) {
        return todoListService.updateTodoList(todoListId, requestDTO.getTitle(), principal.getEmail());
    }
}
