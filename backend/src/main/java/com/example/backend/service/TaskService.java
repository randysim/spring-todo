package com.example.backend.service;

import com.example.backend.dto.inbound.TaskRequestDTO;
import com.example.backend.model.Task;
import com.example.backend.model.TodoList;
import com.example.backend.model.User;
import com.example.backend.repository.TaskRepository;
import com.example.backend.repository.TodoListRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TodoListRepository todoListRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, TodoListRepository todoListRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.todoListRepository = todoListRepository;
        this.userRepository = userRepository;
    }

    /* HELPERS */
    private Map.Entry<User, TodoList> validateUser(String userEmail, Long todoListId) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);

        if (userOptional.isEmpty()) {
            throw new IllegalStateException("User not found");
        }

        User user = userOptional.get();

        Optional<TodoList> todoListOptional = todoListRepository.findById(todoListId);

        if (todoListOptional.isEmpty()) {
            throw new IllegalStateException("Todo List not found");
        }

        TodoList todoList = todoListOptional.get();

        if (!todoList.getUser().getEmail().equals(user.getEmail())) {
            throw new IllegalStateException("User does not own the Todo List.");
        }

        return new AbstractMap.SimpleImmutableEntry<>(user, todoList);
    }

    /* GET TASKS */
    public List<Task> getAllTasks(String userEmail, Long todoListId) {
        Map.Entry<User, TodoList> userAndTodoList = validateUser(userEmail, todoListId);
        TodoList todoList = userAndTodoList.getValue();

        return todoList.getTaskList();
    }

    /* CREATE TASK */
    public Task createTask(String userEmail, Long todoListId, TaskRequestDTO requestDTO) {
        Map.Entry<User, TodoList> userAndTodoList = validateUser(userEmail, todoListId);
        TodoList todoList = userAndTodoList.getValue();

        String desc = requestDTO.getDescription();

        if (desc.length() > 10000) {
            throw new IllegalArgumentException("Description exceeds 10000 characters");
        }

        if (desc.isEmpty()) {
            throw new IllegalArgumentException("Description is empty");
        }

        Task task = new Task(requestDTO.getDescription(), LocalDate.now(), requestDTO.getDue());
        todoList.addTask(task);
        todoListRepository.save(todoList);

        return task;
    }

    /* DELETE TASK */
    public void deleteTask(String userEmail, Long todoListId, Long taskId) {
        Map.Entry<User, TodoList> userAndTodoList = validateUser(userEmail, todoListId);
        TodoList todoList = userAndTodoList.getValue();
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isEmpty()) {
            throw new IllegalStateException("Task not found");
        }

        Task task = taskOptional.get();

        if (!task.getTodoList().getId().equals(todoList.getId())) {
            throw new IllegalStateException("Task does not belong to Todo List");
        }

        todoList.removeTask(task);
        todoListRepository.save(todoList);
    }

    /* UPDATE TASK */
    public Task updateTask(String userEmail, Long todoListId, Long taskId, TaskRequestDTO requestDTO) {
        Map.Entry<User, TodoList> userAndTodoList = validateUser(userEmail, todoListId);
        TodoList todoList = userAndTodoList.getValue();
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isEmpty()) {
            throw new IllegalStateException("Task not found");
        }

        Task task = taskOptional.get();

        if (!task.getTodoList().getId().equals(todoList.getId())) {
            throw new IllegalStateException("Task does not belong to Todo List");
        }

        String desc = requestDTO.getDescription();

        if (desc.length() > 10000) {
            throw new IllegalArgumentException("Description exceeds 10000 characters");
        }

        if (desc.isEmpty()) {
            throw new IllegalArgumentException("Description is empty");
        }

        task.setDescription(desc);
        task.setDue(requestDTO.getDue());
        task.setCompleted(requestDTO.getCompleted());

        return task;
    }
}
