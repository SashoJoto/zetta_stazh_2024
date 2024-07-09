package dev.zettalove.zettalove.exceptions.accountstatus;

public class AccountImagesMissingException extends AccountStatusException{
    public AccountImagesMissingException() {
        super("Profile images are missing.");
    }
}
