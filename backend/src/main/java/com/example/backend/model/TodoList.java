package com.example.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "todo_list")
public class TodoList {
    @Id
    @SequenceGenerator(
            name = "todolist_sequence",
            sequenceName = "todolist_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "todolist_sequence"
    )
    private Long id;
    private String title;
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> taskList;

    public TodoList() {}
    public TodoList(Long id, String title, LocalDate createdAt, User user) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.user = user;
    }
    public TodoList(String title, LocalDate createdAt, User user) {
        this.title = title;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) { this.user = user; }

    public void addTask(Task task) {
        task.setTodoList(this);
        taskList.add(task);
    }

    public void removeTask(Task task) {
        task.setTodoList(null);
        taskList.remove(task);
    }

    public List<Task> getTaskList() {
        return taskList;
    }
}
