package com.kristiana.culturalevents.domain.dto;

import com.kristiana.culturalevents.persitance.entity.Entity;
import java.util.UUID;

public class LocationUpdateDto extends Entity {

    private final String name;
    private final String description;

    public LocationUpdateDto(UUID id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }
}
