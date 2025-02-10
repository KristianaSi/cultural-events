package com.kristiana.culturalevents.persitance.repository.contract;

import com.kristiana.culturalevents.persitance.entity.CustomEvent;
import com.kristiana.culturalevents.persitance.repository.Repository;
import java.time.LocalDate;
import java.util.Set;

public interface EventRepository extends Repository<CustomEvent> {

    Set<CustomEvent> findByDate(LocalDate date);

    void update(CustomEvent customEvent);
}
