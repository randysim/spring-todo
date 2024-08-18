package com.example.backend.dto.inbound;

import java.time.LocalDate;

public class TaskRequestDTO {
    private String description;
    private LocalDate due;
    private Boolean completed;

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
