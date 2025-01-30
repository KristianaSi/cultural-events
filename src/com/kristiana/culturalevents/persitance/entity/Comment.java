package com.kristiana.culturalevents.persitance.entity;

import java.util.UUID;

public class Comment extends Entity {

    String name;
    EventLocation eventLocation;
    CustomEvent customEvent;
    User author;

    public Comment(UUID id, String name, EventLocation eventLocation, CustomEvent customEvent,
        User author) {
        super(id);
        this.name = name;
        this.eventLocation = eventLocation;
        this.customEvent = customEvent;
        this.author = author;

    }

    public String getName() {
        return name;
    }

    public EventLocation getEventLocation() {
        return eventLocation;
    }

    public CustomEvent getCustomEvent() {
        return customEvent;
    }

    public User getAuthor() {
        return author;
    }
    
}
