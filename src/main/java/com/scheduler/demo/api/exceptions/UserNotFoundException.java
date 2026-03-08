package com.scheduler.demo.api.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID uuid) {
        super("Could not find a user by id: " + uuid.toString());
    }
}