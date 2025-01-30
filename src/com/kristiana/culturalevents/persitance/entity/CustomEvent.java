package com.kristiana.culturalevents.persitance.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CustomEvent extends Entity {

    String name;
    List<User> participators;
    LocalDate date;
    EventLocation eventLocation;

    public CustomEvent(UUID id, String name, List<User> participators, EventLocation eventLocation,
        LocalDate date) {
        super(id);
        this.name = name;
        this.participators = participators;
        this.date = date;
        this.eventLocation = eventLocation;
    }

    public String getName() {
        return name;
    }

    public List<User> getParticipators() {
        return participators;
    }

    public LocalDate getDate() {
        return date;
    }

    public EventLocation getEventLocation() {
        return eventLocation;
    }
}
