package dev.zettalove.zettalove.exceptions.userexceptions;

import java.util.UUID;

public class UserAlreadyLikedException extends UserException{
    public UserAlreadyLikedException(UUID userId) {
        super(String.format("User with id %s is already liked", userId.toString()));
    }
}
