package com.kristiana.culturalevents.persitance.repository;

import com.kristiana.culturalevents.persitance.repository.contract.CommentRepository;
import com.kristiana.culturalevents.persitance.repository.contract.EventRepository;
import com.kristiana.culturalevents.persitance.repository.contract.LocationRepository;
import com.kristiana.culturalevents.persitance.repository.contract.UserRepository;

public abstract class RepositoryFactory {

    public static RepositoryFactory getRepositoryFactory() {
        return JsonRepositoryFactory.getInstance();
    }

    public abstract CommentRepository getCommentRepository();

    public abstract UserRepository getUserRepository();

    public abstract LocationRepository getLocationRepository();

    public abstract EventRepository getEventRepository();

    public abstract void commit();
}
