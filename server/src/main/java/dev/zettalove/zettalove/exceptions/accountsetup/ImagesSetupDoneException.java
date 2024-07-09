package dev.zettalove.zettalove.exceptions.accountsetup;

public class ImagesSetupDoneException extends AccountSetupException{
    public ImagesSetupDoneException() {
        super("Images have already been set up.");
    }
}
