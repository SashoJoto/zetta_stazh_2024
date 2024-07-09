package dev.zettalove.zettalove.exceptions.userexceptions;

import java.util.UUID;

public class UserNotFoundException extends UserException{
    public UserNotFoundException(UUID userId) {
        super(String.format("User with id: %s not found", userId.toString()));
    }
}
