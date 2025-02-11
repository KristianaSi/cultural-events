package com.kristiana.culturalevents.domain.impl;

import com.kristiana.culturalevents.domain.contract.AuthService;
import com.kristiana.culturalevents.domain.exception.AuthException;
import com.kristiana.culturalevents.domain.exception.UserAlreadyAuthException;
import com.kristiana.culturalevents.persitance.entity.User;
import com.kristiana.culturalevents.persitance.repository.contract.UserRepository;
import org.mindrot.bcrypt.BCrypt;

final class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private User user;

    AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(String username, String password) {
        if (user != null) {
            throw new UserAlreadyAuthException("Ви вже авторизувалися як: %s"
                .formatted(user.getUsername()));
        }

        User foundedUser = userRepository.findByUsername(username)
            .orElseThrow(AuthException::new);

        if (!BCrypt.checkpw(password, foundedUser.getPassword())) {
            return false;
        }

        user = foundedUser;
        return true;
    }

    public boolean isAuthenticated() {
        return user != null;
    }

    public User getUser() {
        return user;
    }

    public void logout() {
        if (user == null) {
            throw new UserAlreadyAuthException("Ви ще не автентифікавані.");
        }
        user = null;
    }
}
