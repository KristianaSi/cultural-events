package com.kristiana.culturalevents.persitance.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment extends Entity implements Comparable<Comment> {

    private final LocalDateTime createdAt;
    private final User author;
    private final CustomEvent customEvent;
    private LocalDateTime updatedAt;
    private String body;

    public Comment(UUID id, LocalDateTime createdAt, String body, CustomEvent customEvent,
        User author, LocalDateTime updatedAt) {
        super(id);

        this.customEvent = customEvent;
        this.createdAt = createdAt;
        this.body = body;
        this.author = author;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public CustomEvent getCustomEvent() {
        return customEvent;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int compareTo(Comment o) {
        return this.createdAt.compareTo(o.createdAt);
    }

    @Override
    public String toString() {
        return "Comment{" +
            "createdAt=" + createdAt + '\'' +
            ", body='" + body + '\'' +
            ", author=" + author + '\'' +
            ", updatedAt=" + updatedAt + '\'' +
            ", id=" + id +
            '}';
    }
}
