package dev.zettalove.zettalove.exceptions.userimageexceptions;

public class UserImageNotFoundException extends UserImageException{
    public UserImageNotFoundException(Long imageId) {
        super(String.format("User image with id: %s not found", imageId.toString()));
    }
}
