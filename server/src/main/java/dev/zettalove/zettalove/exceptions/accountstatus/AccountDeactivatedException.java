package dev.zettalove.zettalove.exceptions.accountstatus;

public class AccountDeactivatedException extends AccountStatusException{
    public AccountDeactivatedException() {
        super("Account is deactivated.");
    }
}
