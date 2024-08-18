package com.example.backend.model;

import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDate;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Long id;
    private String description;
    private Boolean completed;
    private LocalDate due;
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todolist_id")
    @JsonIgnore
    private TodoList todoList;

    public Task() {}
    public Task(String description, LocalDate createdAt, LocalDate due) {
        this.description = description;
        this.due = due;
        this.createdAt = createdAt;
        this.completed = false;
    }

    public Task(String description, LocalDate createdAt) {
        this.description = description;
        this.createdAt = createdAt;
        this.completed = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getDue() {
        return due;
    }

    public void setDue(LocalDate due) {
        this.due = due;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public TodoList getTodoList() { return todoList; }

    public void setTodoList(TodoList todoList) { this.todoList = todoList; }
}
