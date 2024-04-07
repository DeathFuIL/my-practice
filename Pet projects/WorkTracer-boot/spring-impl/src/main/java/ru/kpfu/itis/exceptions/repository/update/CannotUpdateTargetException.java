package ru.kpfu.itis.exceptions.repository.update;

public class CannotUpdateTargetException extends CannotUpdateRepositoryException {

    public CannotUpdateTargetException() {
        super("Failed to update target information, try again");
    }
}
