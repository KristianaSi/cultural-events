package com.kristiana.culturalevents.persitance.entity;

import java.time.LocalDate;
import java.util.UUID;

public class CustomEvent extends Entity implements Comparable<CustomEvent> {

    private String name;
    private String participators;
    private LocalDate date;
    private EventLocation eventLocation;
    private String description;

    public CustomEvent(UUID id, String name, String participators,
        EventLocation eventLocation, LocalDate date, String description) {
        super(id);
        this.name = name;
        this.participators = participators;
        this.date = date;
        this.eventLocation = eventLocation;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParticipators() {
        return participators;
    }

    public void setParticipators(String participators) {
        this.participators = participators;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public EventLocation getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(EventLocation location) {
        this.eventLocation = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(CustomEvent o) {
        return this.name.compareTo(o.name);
    }
}
