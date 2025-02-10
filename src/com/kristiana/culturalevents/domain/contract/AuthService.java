package com.kristiana.culturalevents.domain.contract;

import com.kristiana.culturalevents.persitance.entity.User;

public interface AuthService {

    boolean authenticate(String username, String password);

    boolean isAuthenticated();

    User getUser();

    void logout();
}
