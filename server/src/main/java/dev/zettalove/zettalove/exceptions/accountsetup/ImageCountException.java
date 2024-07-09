package dev.zettalove.zettalove.exceptions.accountsetup;

public class ImageCountException extends AccountSetupException{
    public ImageCountException() {
        super("You must provide minimum 1 image and a maximum of 3 images.");
    }
}
