package com.kristiana.culturalevents.persitance.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kristiana.culturalevents.persitance.entity.EventLocation;
import com.kristiana.culturalevents.persitance.entity.User;
import com.kristiana.culturalevents.persitance.repository.contract.LocationRepository;
import java.util.Optional;
import java.util.Set;

final class LocationJsonRepositoryImpl extends GenericJsonRepository<EventLocation> implements
    LocationRepository {

    LocationJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.LOCATIONS.getPath(), TypeToken
            .getParameterized(Set.class, User.class)
            .getType());
    }

    @Override
    public Optional<EventLocation> findByName(String name) {
        return entities.stream().filter(u -> u.getName().equals(name)).findFirst();
    }
}
