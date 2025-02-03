package com.kristiana.culturalevents.persitance.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kristiana.culturalevents.persitance.entity.CustomEvent;
import com.kristiana.culturalevents.persitance.entity.User;
import com.kristiana.culturalevents.persitance.repository.contract.EventRepository;
import java.util.Optional;
import java.util.Set;

final class EventJsonRepositoryImpl extends GenericJsonRepository<CustomEvent> implements
    EventRepository {

    EventJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.EVENTS.getPath(), TypeToken
            .getParameterized(Set.class, User.class)
            .getType());
    }

    @Override
    public Optional<CustomEvent> findByName(String name) {
        return entities.stream().filter(u -> u.getName().equals(name)).findFirst();
    }
}
