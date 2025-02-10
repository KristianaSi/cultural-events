package com.kristiana.culturalevents.domain.impl;

import com.kristiana.culturalevents.domain.contract.EventService;
import com.kristiana.culturalevents.domain.dto.EventAddDto;
import com.kristiana.culturalevents.domain.dto.EventUpdateDto;
import com.kristiana.culturalevents.persitance.entity.CustomEvent;
import com.kristiana.culturalevents.persitance.exception.EntityArgumentException;
import com.kristiana.culturalevents.persitance.repository.contract.EventRepository;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Predicate;

public final class EventServiceImpl extends GenericService<CustomEvent> implements EventService {

    EventRepository eventRepository;

    public EventServiceImpl(
        EventRepository eventRepository) {
        super(eventRepository);
        this.eventRepository = eventRepository;
    }

    @Override
    public Set<CustomEvent> getByDate(LocalDate date) {
        return new TreeSet<>(eventRepository.findByDate(date));
    }

    @Override
    public CustomEvent add(EventAddDto eventAddDto) {
        try {
            CustomEvent event = new CustomEvent(
                eventAddDto.getId(),
                eventAddDto.name(),
                eventAddDto.participators(),
                eventAddDto.eventLocation(),
                eventAddDto.date(),
                eventAddDto.description()
            );

            eventRepository.add(event);
            return event;
        } catch (EntityArgumentException e) {
            throw new IllegalArgumentException(String.join(", ", e.getErrors()));
        }
    }

    @Override
    public void update(EventUpdateDto eventUpdateDto) {
        CustomEvent existingEvent = get(eventUpdateDto.getId());

        existingEvent.setName(eventUpdateDto.name());
        existingEvent.setEventLocation(eventUpdateDto.eventLocation());
        existingEvent.setParticipators(eventUpdateDto.participators());
        existingEvent.setDate(eventUpdateDto.date());
        existingEvent.setDescription(eventUpdateDto.description());

        eventRepository.update(existingEvent);
    }

    @Override
    public void delete(UUID eventId) {
        CustomEvent eventToRemove = get(eventId);
        eventRepository.remove(eventToRemove);
    }

    @Override
    public Set<CustomEvent> getAll() {
        return getAll(c -> true);
    }

    @Override
    public Set<CustomEvent> getAll(Predicate<CustomEvent> filter) {
        return new TreeSet<>(eventRepository.findAll(filter));
    }
}
