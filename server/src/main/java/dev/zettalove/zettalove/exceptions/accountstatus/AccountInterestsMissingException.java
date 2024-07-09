package dev.zettalove.zettalove.exceptions.accountstatus;

public class AccountInterestsMissingException extends AccountStatusException{
    public AccountInterestsMissingException() {
        super("Interests are missing.");
    }
}
