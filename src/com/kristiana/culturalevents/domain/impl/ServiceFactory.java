package com.kristiana.culturalevents.domain.impl;

import com.kristiana.culturalevents.domain.contract.AuthService;
import com.kristiana.culturalevents.domain.contract.CommentService;
import com.kristiana.culturalevents.domain.contract.EventService;
import com.kristiana.culturalevents.domain.contract.LocationService;
import com.kristiana.culturalevents.domain.contract.SignUpService;
import com.kristiana.culturalevents.domain.contract.UserService;
import com.kristiana.culturalevents.domain.exception.DependencyException;
import com.kristiana.culturalevents.persitance.repository.RepositoryFactory;

public final class ServiceFactory {

    private static volatile ServiceFactory INSTANCE;
    private final AuthService authService;
    private final CommentService commentService;
    private final LocationService locationService;
    private final EventService eventService;
    private final UserService userService;
    private final SignUpService signUpService;
    private final RepositoryFactory repositoryFactory;

    private ServiceFactory(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
        var userRepository = repositoryFactory.getUserRepository();
        authService = new AuthServiceImpl(userRepository);

        commentService = new CommentServiceImpl(
            repositoryFactory.getCommentRepository(), userRepository);

        locationService = new LocationServiceImpl(repositoryFactory.getLocationRepository());
        eventService = new EventServiceImpl(repositoryFactory.getEventRepository());
        userService = new UserServiceImpl(userRepository);
        signUpService = new SignUpServiceImpl(userService);
    }

    /**
     * Використовувати, лише якщо впевнені, що існує об'єкт RepositoryFactory.
     *
     * @return екземпляр типу ServiceFactory
     */
    public static ServiceFactory getInstance() {
        if (INSTANCE.repositoryFactory != null) {
            return INSTANCE;
        } else {
            throw new DependencyException(
                "Ви забули створити обєкт RepositoryFactory, перед використанням ServiceFactory.");
        }
    }

    public static ServiceFactory getInstance(RepositoryFactory repositoryFactory) {
        if (INSTANCE == null) {
            synchronized (ServiceFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceFactory(repositoryFactory);
                }
            }
        }

        return INSTANCE;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public EventService getEventService() {
        return eventService;
    }

    public CommentService getCommentService() {
        return commentService;
    }

    public LocationService getLocationService() {
        return locationService;
    }

    public UserService getUserService() {
        return userService;
    }

    public SignUpService getSignUpService() {
        return signUpService;
    }

    public RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }
}
