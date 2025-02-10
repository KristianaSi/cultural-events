package com.kristiana.culturalevents.domain.dto;

import com.kristiana.culturalevents.persitance.entity.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

public class CommentUpdateDto extends Entity {

    private final String body;
    private final LocalDateTime updatedAt;

    public CommentUpdateDto(UUID id, String body, LocalDateTime updatedAt) {
        super(id);
        this.body = body;
        this.updatedAt = updatedAt;
    }

    public String body() {
        return body;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }
}
