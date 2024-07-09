package dev.zettalove.zettalove.exceptions.accountsetup;

public class InterestSetupDoneException extends AccountSetupException{
    public InterestSetupDoneException() {
        super("Interests have already been set up.");
    }
}
