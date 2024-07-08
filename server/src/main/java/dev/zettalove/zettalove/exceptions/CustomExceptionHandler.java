package dev.zettalove.zettalove.exceptions;

import dev.zettalove.zettalove.exceptions.accountstatus.*;
import dev.zettalove.zettalove.exceptions.registerexceptions.EmailFormatException;
import dev.zettalove.zettalove.exceptions.registerexceptions.EmailTakenException;
import dev.zettalove.zettalove.exceptions.registerexceptions.PasswordLengthException;
import dev.zettalove.zettalove.exceptions.registerexceptions.RegisterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RegisterException.class)
    public ProblemDetail handleException(RegisterException e) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(
                        HttpStatusCode.valueOf(500), e.toString());

        if (e instanceof EmailFormatException) {
            problemDetail = ProblemDetail
                    .forStatusAndDetail(
                            HttpStatus.BAD_REQUEST, e.getMessage());
            problemDetail.setProperty(
                    "error", "email_format"
            );
        } else if (e instanceof EmailTakenException) {
            problemDetail = ProblemDetail
                    .forStatusAndDetail(
                            HttpStatus.CONFLICT, e.getMessage());
            problemDetail.setProperty(
                    "error", "email_taken"
            );
        } else if (e instanceof PasswordLengthException){
            problemDetail = ProblemDetail
                    .forStatusAndDetail(
                            HttpStatus.BAD_REQUEST, e.getMessage());
            problemDetail.setProperty(
                    "error", "password_length"
            );
        }

        e.printStackTrace();

        return problemDetail;
    }

    @ExceptionHandler(AccountStatusException.class)
    public ProblemDetail handleAccountStatusException(AccountStatusException e) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(
                        HttpStatusCode.valueOf(500), e.toString());

        if (e instanceof AccountNotCompleteException) {
            problemDetail = ProblemDetail
                    .forStatusAndDetail(
                            HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
            problemDetail.setProperty(
                    "error", "account_not_complete"
            );
        } else if (e instanceof AccountDeactivatedException) {
            problemDetail = ProblemDetail
                    .forStatusAndDetail(
                            HttpStatus.FORBIDDEN, e.getMessage());
            problemDetail.setProperty(
                    "error", "account_deactivated"
            );
        } else if (e instanceof AccountInterestsMissingException) {
            problemDetail = ProblemDetail
                    .forStatusAndDetail(
                            HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
            problemDetail.setProperty(
                    "error", "account_interests_missing"
            );
        } else if (e instanceof AccountImagesMissingException) {
            problemDetail = ProblemDetail
                    .forStatusAndDetail(
                            HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
            problemDetail.setProperty(
                    "error", "account_images_missing"
            );
        }

        e.printStackTrace();

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleNonCustomException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(
                        HttpStatusCode.valueOf(500), e.toString());

        e.printStackTrace();

        return problemDetail;
    }

}
