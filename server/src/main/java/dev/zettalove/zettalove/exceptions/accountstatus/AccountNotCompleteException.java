package dev.zettalove.zettalove.exceptions.accountstatus;

public class AccountNotCompleteException extends AccountStatusException {
    public AccountNotCompleteException() {
        super("Account is not complete.");
    }
}
