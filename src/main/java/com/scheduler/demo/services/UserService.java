package com.scheduler.demo.services;

import com.scheduler.demo.dtos.CreateUserDto;
import com.scheduler.demo.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User createUser(CreateUserDto dto);

    User getUser(UUID userId);
}
