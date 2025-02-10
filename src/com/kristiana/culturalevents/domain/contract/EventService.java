package com.kristiana.culturalevents.domain.contract;

import com.kristiana.culturalevents.domain.Service;
import com.kristiana.culturalevents.domain.dto.EventAddDto;
import com.kristiana.culturalevents.domain.dto.EventUpdateDto;
import com.kristiana.culturalevents.persitance.entity.CustomEvent;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface EventService extends Service<CustomEvent> {

    Set<CustomEvent> getByDate(LocalDate date);

    CustomEvent add(EventAddDto eventAddDto);

    void update(EventUpdateDto eventUpdateDto);

    void delete(UUID id);
}
