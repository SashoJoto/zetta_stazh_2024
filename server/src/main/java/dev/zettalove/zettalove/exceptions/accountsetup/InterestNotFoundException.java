package dev.zettalove.zettalove.exceptions.accountsetup;

public class InterestNotFoundException extends AccountSetupException{
    public InterestNotFoundException() {
        super("Interest not found.");
    }
}
