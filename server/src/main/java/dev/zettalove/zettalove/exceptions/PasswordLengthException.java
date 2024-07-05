package dev.zettalove.zettalove.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST, reason = "password_format")
public class PasswordLengthException extends RuntimeException {
    public PasswordLengthException() {
        super("Password must be at least 8 characters long.");
    }
}
