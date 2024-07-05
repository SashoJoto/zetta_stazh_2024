package dev.zettalove.zettalove.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "email_taken")
public class EmailTakenException extends RuntimeException{
    public EmailTakenException() {
        super("Email is already taken");
    }
}
