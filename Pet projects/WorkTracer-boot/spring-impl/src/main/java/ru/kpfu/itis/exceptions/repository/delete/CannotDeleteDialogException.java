package ru.kpfu.itis.exceptions.repository.delete;

public class CannotDeleteDialogException extends CannotDeleteRepositoryException {
    public CannotDeleteDialogException() {
        super("Failed to delete dialog, try again");
    }
}
