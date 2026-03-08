package com.scheduler.demo.services;

import com.scheduler.demo.api.exceptions.UserNotFoundException;
import com.scheduler.demo.domain.entities.User;
import com.scheduler.demo.repositories.UserRepository;

import com.scheduler.demo.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl testee;

    @Test
    public void getUser() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = testee.getUser(userId);
        assertEquals(userId, result.getId());
    }

    @Test
    public void testGetNotExistingUser() {
        UUID userId = UUID.randomUUID();

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            testee.getUser(userId);
        });
    }
}
