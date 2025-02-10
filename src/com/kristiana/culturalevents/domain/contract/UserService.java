package com.kristiana.culturalevents.domain.contract;

import com.kristiana.culturalevents.domain.Service;
import com.kristiana.culturalevents.domain.dto.UserAddDto;
import com.kristiana.culturalevents.persitance.entity.User;

public interface UserService extends Service<User> {

    User getByUsername(String username);

    User getByEmail(String email);

    User add(UserAddDto userAddDto);
}
