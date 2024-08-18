package com.example.backend.controller;

import com.example.backend.dto.inbound.TaskRequestDTO;
import com.example.backend.model.Task;
import com.example.backend.security.GoogleOAuth2User;
import com.example.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/todolists/{todoListId}/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks(
            @AuthenticationPrincipal GoogleOAuth2User principal,
            @PathVariable("todoListId") Long todoListId
    ) {
        return taskService.getAllTasks(principal.getEmail(), todoListId);
    }

    @PostMapping
    public Task createTask(
            @AuthenticationPrincipal GoogleOAuth2User principal,
            @PathVariable("todoListId") Long todoListId,
            @RequestBody TaskRequestDTO requestDTO
    ) {
        return taskService.createTask(principal.getEmail(), todoListId, requestDTO);
    }

    @PutMapping
    public Task updateTask(
            @AuthenticationPrincipal GoogleOAuth2User principal,
            @PathVariable("todoListId") Long todoListId,
            @RequestBody TaskRequestDTO requestDTO,
            @PathVariable("taskId") Long taskId
    ) {
        return taskService.updateTask(principal.getEmail(), todoListId, taskId, requestDTO);
    }

    @DeleteMapping
    public void deleteTask(
            @AuthenticationPrincipal GoogleOAuth2User principal,
            @PathVariable("todoListId") Long todoListId,
            @PathVariable("taskId") Long taskId
    ) {
        taskService.deleteTask(principal.getEmail(), todoListId, taskId);
    }
}
