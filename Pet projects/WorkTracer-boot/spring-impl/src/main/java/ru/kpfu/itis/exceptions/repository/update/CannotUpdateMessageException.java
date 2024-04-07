package ru.kpfu.itis.exceptions.repository.update;

public class CannotUpdateMessageException extends CannotUpdateRepositoryException {
    public CannotUpdateMessageException() {
        super("Failed to update message, try again");
    }
}
