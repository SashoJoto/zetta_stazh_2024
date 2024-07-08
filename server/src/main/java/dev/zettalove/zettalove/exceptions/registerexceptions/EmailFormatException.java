package dev.zettalove.zettalove.exceptions.registerexceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST, reason = "email_format")
public class EmailFormatException extends RegisterException {
    public EmailFormatException() {
        super("Invalid email format");
    }
}
