package com.kristiana.culturalevents.domain.impl;

import com.kristiana.culturalevents.domain.contract.SignUpService;
import com.kristiana.culturalevents.domain.contract.UserService;
import com.kristiana.culturalevents.domain.dto.UserAddDto;

final class SignUpServiceImpl implements SignUpService {

    private final UserService userService;

    SignUpServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void signUp(UserAddDto userAddDto) {
        userService.add(userAddDto);
    }
}
