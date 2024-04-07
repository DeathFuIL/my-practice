package ru.kpfu.itis.exceptions.repository.delete;

public class CannotDeleteMessageException extends CannotDeleteRepositoryException {
    public CannotDeleteMessageException() {
        super("Failed to delete message, try again");
    }
}
