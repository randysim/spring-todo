package com.example.backend.service;

import com.example.backend.dto.outbound.TodoListResponseDTO;
import com.example.backend.model.TodoList;
import com.example.backend.model.User;
import com.example.backend.repository.TodoListRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoListService {
    private final TodoListRepository todoListRepository;
    private final UserRepository userRepository;

    @Autowired
    public TodoListService(TodoListRepository todoListRepository, UserRepository userRepository) {
        this.todoListRepository = todoListRepository;
        this.userRepository = userRepository;
    }

    /* GET TODO-LISTS */
    public List<TodoListResponseDTO> getUserTodoLists(String userEmail) {
        return todoListRepository.findAllByUserEmail(userEmail)
                .stream()
                .map(todo -> new TodoListResponseDTO(todo.getTitle(), todo.getId()))
                .collect(Collectors.toList());
    }

    /* CREATE TODO-LISTS */
    public TodoListResponseDTO createTodoList(String todoListTitle, String userEmail) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOptional.get();

        Optional<TodoList> todoOptional = todoListRepository.findByTitleAndUserId(todoListTitle, user.getId());

        if (todoOptional.isPresent()) {
            throw new IllegalStateException("Todo List with name already taken.");
        }

        TodoList todoList = new TodoList(todoListTitle, LocalDate.now(), user);
        user.addTodoList(todoList);
        userRepository.save(user);

        return new TodoListResponseDTO(todoList.getTitle(), todoList.getId());
    }

    /* UPDATE TODO-LISTS */
    /* DELETE TODO-LISTS*/
}