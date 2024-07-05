package dev.zettalove.zettalove.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(
                        HttpStatusCode.valueOf(500), e.toString());

        if (e instanceof BadCredentialsException) {
            problemDetail = ProblemDetail
                    .forStatusAndDetail(
                            HttpStatusCode.valueOf(401), "Invalid credentials");
            problemDetail.setProperty(
                    "access_denied", "Invalid credentials"
            );
        }

        e.printStackTrace();

        return problemDetail;
    }



}
