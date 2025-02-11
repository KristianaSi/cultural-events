package com.kristiana.culturalevents.persitance.entity;

import java.util.UUID;

public class EventLocation extends Entity implements Comparable<EventLocation> {

    private String name;
    private String description;

    public EventLocation(UUID id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(EventLocation o) {
        return this.name.compareTo(o.name);
    }
}
