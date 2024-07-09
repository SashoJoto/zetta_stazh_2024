package dev.zettalove.zettalove.exceptions.userimageexceptions;

public class UserImageCouldNotBeRemovedException extends UserImageException {
    public UserImageCouldNotBeRemovedException() {
        super("User image could not be removed because user wont have any images left.");
    }
}
