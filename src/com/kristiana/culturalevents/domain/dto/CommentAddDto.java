package com.kristiana.culturalevents.domain.dto;

import com.kristiana.culturalevents.persitance.entity.CustomEvent;
import com.kristiana.culturalevents.persitance.entity.Entity;
import com.kristiana.culturalevents.persitance.entity.User;
import java.time.LocalDateTime;
import java.util.UUID;

public class CommentAddDto extends Entity {

    private final LocalDateTime createdAt;
    private final User author;
    private final CustomEvent customEvent;
    private final LocalDateTime updatedAt;
    private final String body;

    public CommentAddDto(UUID id, LocalDateTime createdAt, String body, CustomEvent customEvent,
        User author, LocalDateTime updatedAt) {
        super(id);

        this.customEvent = customEvent;
        this.createdAt = createdAt;
        this.body = body;
        this.author = author;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public String body() {
        return body;
    }

    public CustomEvent customEvent() {
        return customEvent;
    }

    public User author() {
        return author;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }
}
