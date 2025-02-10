package com.kristiana.culturalevents.persitance.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kristiana.culturalevents.domain.exception.EntityNotFoundException;
import com.kristiana.culturalevents.persitance.entity.CustomEvent;
import com.kristiana.culturalevents.persitance.repository.contract.EventRepository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

final class EventJsonRepositoryImpl extends GenericJsonRepository<CustomEvent> implements
    EventRepository {

    EventJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.EVENTS.getPath(), TypeToken
            .getParameterized(Set.class, CustomEvent.class)
            .getType());
    }

    @Override
    public Set<CustomEvent> findByDate(LocalDate date) {
        return entities.stream()
            .filter(item -> date.equals(item.getDate()))
            .collect(Collectors.toSet());
    }

    @Override
    public CustomEvent add(CustomEvent event) {
        super.add(event);

        JsonRepositoryFactory.getInstance().commit();
        return event;
    }

    @Override
    public void update(CustomEvent customEvent) {
        Optional<CustomEvent> existingEvent = entities.stream()
            .filter(c -> c.getId().equals(customEvent.getId()))
            .findFirst();

        if (existingEvent.isPresent()) {
            super.update(customEvent);
        } else {
            throw new EntityNotFoundException("Подія не існує.");
        }
        JsonRepositoryFactory.getInstance().commit();
    }

    @Override
    public boolean remove(CustomEvent event) {
        boolean removed = entities.remove(event);
        if (removed) {
            JsonRepositoryFactory.getInstance().commit();
        }
        return removed;
    }
}
