package com.kristiana.culturalevents.domain.impl;

import com.kristiana.culturalevents.domain.contract.UserService;
import com.kristiana.culturalevents.domain.dto.UserAddDto;
import com.kristiana.culturalevents.domain.exception.EntityNotFoundException;
import com.kristiana.culturalevents.persitance.entity.User;
import com.kristiana.culturalevents.persitance.repository.contract.UserRepository;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import org.mindrot.bcrypt.BCrypt;

final class UserServiceImpl extends GenericService<User> implements UserService {

    private final UserRepository userRepository;

    UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Такого користувача не існує."));
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByUsername(email)
            .orElseThrow(() -> new EntityNotFoundException("Такого користувача не існує."));
    }

    @Override
    public Set<User> getAll() {
        return getAll(u -> true);
    }

    @Override
    public Set<User> getAll(Predicate<User> filter) {
        return new TreeSet<>(userRepository.findAll(filter));
    }

    @Override
    public User add(UserAddDto userAddDto) {
        var user = new User(userAddDto.getId(),
            BCrypt.hashpw(userAddDto.rawPassword(), BCrypt.gensalt()),
            userAddDto.email(),
            userAddDto.birthday(),
            userAddDto.username(),
            userAddDto.role());
        userRepository.add(user);
        return user;
    }
}
