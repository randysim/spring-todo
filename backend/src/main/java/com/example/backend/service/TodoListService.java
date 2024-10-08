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

    public TodoListResponseDTO getTodoList(String userEmail, Long id) {
        Optional<TodoList> todoListOptional = todoListRepository.findById(id);

        if (todoListOptional.isEmpty()) {
            throw new IllegalStateException("Invalid Todo List ID.");
        }

        TodoList todoList = todoListOptional.get();

        if (!todoList.getUser().getEmail().equals(userEmail)) {
            throw new IllegalStateException("User does not own Todo List.");
        }

        return new TodoListResponseDTO(todoList.getTitle(), todoList.getId());
    }

    /* CREATE TODO-LISTS */
    public TodoListResponseDTO createTodoList(String todoListTitle, String userEmail) {
        if (todoListTitle.length() > 100) {
            throw new IllegalStateException("Todo List title exceeds 100 characters.");
        }

        if (todoListTitle.isEmpty()) {
            throw new IllegalStateException("Todo List can't be empty.");
        }

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
        todoListRepository.save(todoList);

        return new TodoListResponseDTO(todoList.getTitle(), todoList.getId());
    }

    /* UPDATE TODO-LISTS */
    public TodoListResponseDTO updateTodoList(Long todoListId, String todoListTitle, String userEmail) {
        if (todoListTitle.length() > 100) {
            throw new IllegalStateException("Todo List title exceeds 100 characters.");
        }

        if (todoListTitle.isEmpty()) {
            throw new IllegalStateException("Todo List can't be empty.");
        }

        Optional<TodoList> todoListOptional = todoListRepository.findById(todoListId);

        if (todoListOptional.isEmpty()) {
            throw new IllegalStateException("Invalid Todo List ID.");
        }

        TodoList todoList = todoListOptional.get();

        if (!todoList.getUser().getEmail().equals(userEmail)) {
            throw new IllegalStateException("User does not own Todo List with given ID.");
        }

        todoList.setTitle(todoListTitle);
        todoListRepository.save(todoList);

        return new TodoListResponseDTO(todoListTitle, todoListId);
    }

    /* DELETE TODO-LISTS*/
    public void deleteTodoList(Long todoListId, String userEmail) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOptional.get();

        Optional<TodoList> todoListOptional = todoListRepository.findById(todoListId);

        if (todoListOptional.isEmpty()) {
            throw new IllegalArgumentException("Todo list not found.");
        }

        TodoList todoList = todoListOptional.get();

        if (!todoList.getUser().getEmail().equals(user.getEmail())) {
            throw new IllegalStateException("User does not own Todo List with given ID.");
        }

        user.removeTodoList(todoList);
        userRepository.save(user);
    }
}