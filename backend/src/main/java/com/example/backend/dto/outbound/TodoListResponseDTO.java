package com.example.backend.dto.outbound;

public class TodoListResponseDTO {
    private String title;
    private Long id;

    public TodoListResponseDTO() {}
    public TodoListResponseDTO(String title, Long id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
