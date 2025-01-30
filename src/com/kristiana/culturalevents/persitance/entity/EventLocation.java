package com.kristiana.culturalevents.persitance.entity;

import java.util.UUID;

public class EventLocation extends Entity {

    private final String name;
    private final String description;

    public EventLocation(UUID id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
