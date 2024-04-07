package ru.kpfu.itis.exceptions.repository.update;

public class CannotUpdateTaskException extends CannotUpdateRepositoryException {

    public CannotUpdateTaskException() {
        super("Failed to update task information, try again");
    }
}
