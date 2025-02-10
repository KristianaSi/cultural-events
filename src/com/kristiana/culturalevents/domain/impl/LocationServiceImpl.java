package com.kristiana.culturalevents.domain.impl;

import com.kristiana.culturalevents.domain.contract.LocationService;
import com.kristiana.culturalevents.domain.dto.LocationAddDto;
import com.kristiana.culturalevents.domain.dto.LocationUpdateDto;
import com.kristiana.culturalevents.persitance.entity.EventLocation;
import com.kristiana.culturalevents.persitance.exception.EntityArgumentException;
import com.kristiana.culturalevents.persitance.repository.contract.LocationRepository;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Predicate;

public final class LocationServiceImpl extends GenericService<EventLocation> implements
    LocationService {

    LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        super(locationRepository);
        this.locationRepository = locationRepository;
    }

    @Override
    public Set<EventLocation> getByName(String name) {
        return Set.of();
    }

    @Override
    public EventLocation add(LocationAddDto locationAddDto) {
        try {
            EventLocation location = new EventLocation(
                locationAddDto.getId(),
                locationAddDto.name(),
                locationAddDto.description()
            );

            locationRepository.add(location);
            return location;
        } catch (EntityArgumentException e) {
            throw new IllegalArgumentException(String.join(", ", e.getErrors()));
        }
    }

    @Override
    public void update(LocationUpdateDto locationUpdateDto) {
        EventLocation eventLocation = get(locationUpdateDto.getId());

        eventLocation.setName(locationUpdateDto.name());
        eventLocation.setDescription(locationUpdateDto.description());

        locationRepository.update(eventLocation);
    }

    @Override
    public void delete(UUID locationId) {
        EventLocation locationToRemove = get(locationId);
        locationRepository.remove(locationToRemove);
    }

    @Override
    public Set<EventLocation> getAll() {
        return getAll(c -> true);
    }

    @Override
    public Set<EventLocation> getAll(Predicate<EventLocation> filter) {
        return new TreeSet<>(locationRepository.findAll(filter));
    }
}
