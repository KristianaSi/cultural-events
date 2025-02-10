package com.kristiana.culturalevents.persitance.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kristiana.culturalevents.domain.exception.EntityNotFoundException;
import com.kristiana.culturalevents.persitance.entity.EventLocation;
import com.kristiana.culturalevents.persitance.repository.contract.LocationRepository;
import java.util.Optional;
import java.util.Set;

final class LocationJsonRepositoryImpl extends GenericJsonRepository<EventLocation> implements
    LocationRepository {

    LocationJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.LOCATIONS.getPath(), TypeToken
            .getParameterized(Set.class, EventLocation.class)
            .getType());
    }

    @Override
    public Optional<EventLocation> findByName(String name) {
        return entities.stream().filter(u -> u.getName().equals(name)).findFirst();
    }

    @Override
    public EventLocation add(EventLocation location) {
        super.add(location);

        JsonRepositoryFactory.getInstance().commit();
        return location;
    }

    @Override
    public void update(EventLocation location) {
        Optional<EventLocation> existingCollection = entities.stream()
            .filter(c -> c.getId().equals(location.getId()))
            .findFirst();

        if (existingCollection.isPresent()) {
            super.update(location);
        } else {
            throw new EntityNotFoundException("Локація не існує.");
        }
        JsonRepositoryFactory.getInstance().commit();
    }

    @Override
    public boolean remove(EventLocation location) {
        boolean removed = entities.remove(location);
        if (removed) {
            JsonRepositoryFactory.getInstance().commit();
        }
        return removed;
    }
}
