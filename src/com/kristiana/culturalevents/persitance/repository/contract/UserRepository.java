package com.kristiana.culturalevents.persitance.repository.contract;

import com.kristiana.culturalevents.persitance.entity.User;
import com.kristiana.culturalevents.persitance.repository.Repository;
import java.util.Optional;

public interface UserRepository extends Repository<User> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
