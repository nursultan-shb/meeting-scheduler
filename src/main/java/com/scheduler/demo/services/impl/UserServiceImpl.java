package com.scheduler.demo.services.impl;

import com.scheduler.demo.api.exceptions.UserNotFoundException;
import com.scheduler.demo.dtos.CreateUserDto;
import com.scheduler.demo.domain.entities.User;
import com.scheduler.demo.repositories.UserRepository;
import com.scheduler.demo.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(CreateUserDto dto) {
        User user = new User();
        user.setEmail(dto.email());
        user.setName(dto.name());

        return userRepository.save(user);
    }

    @Override
    public User getUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
