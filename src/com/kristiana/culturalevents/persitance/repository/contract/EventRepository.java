package com.kristiana.culturalevents.persitance.repository.contract;

import com.kristiana.culturalevents.persitance.entity.CustomEvent;
import com.kristiana.culturalevents.persitance.repository.Repository;
import java.util.Optional;

public interface EventRepository extends Repository<CustomEvent> {

    Optional<CustomEvent> findByName(String name);
}
