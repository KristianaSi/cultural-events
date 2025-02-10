package com.kristiana.culturalevents.domain.dto;

import com.kristiana.culturalevents.persitance.entity.Entity;
import com.kristiana.culturalevents.persitance.entity.EventLocation;
import java.time.LocalDate;
import java.util.UUID;

public class EventUpdateDto extends Entity {

    private final String name;
    private final String participators;
    private final LocalDate date;
    private final EventLocation eventLocation;
    private final String description;

    public EventUpdateDto(UUID id, String name, String participators,
        EventLocation eventLocation, LocalDate date, String description) {
        super(id);
        this.name = name;
        this.participators = participators;
        this.date = date;
        this.eventLocation = eventLocation;
        this.description = description;
    }

    public String name() {
        return name;
    }

    public String participators() {
        return participators;
    }

    public LocalDate date() {
        return date;
    }

    public EventLocation eventLocation() {
        return eventLocation;
    }

    public String description() {
        return description;
    }
}
