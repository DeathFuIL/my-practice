package ru.kpfu.itis.exceptions.repository.delete;

public class CannotDeleteTargetException extends CannotDeleteRepositoryException {

    public CannotDeleteTargetException() {
        super("Failed to delete target, try again");
    }

}
