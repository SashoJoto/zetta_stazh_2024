package dev.zettalove.zettalove.exceptions.registerexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "email_taken")
public class EmailTakenException extends RegisterException{
    public EmailTakenException() {
        super("Email is already taken");
    }
}
