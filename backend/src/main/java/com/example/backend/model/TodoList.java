package com.example.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

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

    public User setUser(User user) { this.user = user; }
}
