package com.kristiana.culturalevents.persitance.repository.contract;

import com.kristiana.culturalevents.persitance.entity.EventLocation;
import com.kristiana.culturalevents.persitance.repository.Repository;
import java.util.Optional;

public interface LocationRepository extends Repository<EventLocation> {

    Optional<EventLocation> findByName(String name);

    void update(EventLocation eventLocation);
}
