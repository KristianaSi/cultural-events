package com.kristiana.culturalevents.domain.impl;

import com.kristiana.culturalevents.domain.Service;
import com.kristiana.culturalevents.domain.exception.EntityNotFoundException;
import com.kristiana.culturalevents.persitance.entity.Entity;
import com.kristiana.culturalevents.persitance.repository.Repository;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public class GenericService<E extends Entity> implements Service<E> {

    private final Repository<E> repository;

    public GenericService(Repository<E> repository) {
        this.repository = repository;
    }

    @Override
    public E get(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Ми не знайшли запис по вказаному ідентифікатору"));
    }

    @Override
    public Set<E> getAll() {
        return repository.findAll();
    }

    @Override
    public Set<E> getAll(Predicate<E> filter) {
        return repository.findAll(filter);
    }

    @Override
    public E add(E entity) {
        return repository.add(entity);
    }

    @Override
    public boolean remove(E entity) {
        return repository.remove(entity);
    }
}
