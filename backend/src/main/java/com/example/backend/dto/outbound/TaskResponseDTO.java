package com.example.backend.dto.outbound;

import java.time.LocalDate;

public class TaskResponseDTO {
    private Long id;
    private String description;
    private LocalDate due;
    private Boolean completed;

    public TaskResponseDTO() { }
    public TaskResponseDTO(Long id, String description, LocalDate due, Boolean completed) {
        this.id = id;
        this.description = description;
        this.due = due;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDue() {
        return due;
    }

    public void setDue(LocalDate due) {
        this.due = due;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
