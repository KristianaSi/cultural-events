package com.kristiana.culturalevents.domain.contract;

import com.kristiana.culturalevents.domain.Service;
import com.kristiana.culturalevents.domain.dto.LocationAddDto;
import com.kristiana.culturalevents.domain.dto.LocationUpdateDto;
import com.kristiana.culturalevents.persitance.entity.EventLocation;
import java.util.Set;
import java.util.UUID;

public interface LocationService extends Service<EventLocation> {

    Set<EventLocation> getByName(String name);

    EventLocation add(LocationAddDto locationAddDto);

    void update(LocationUpdateDto locationUpdateDto);

    void delete(UUID id);
}
