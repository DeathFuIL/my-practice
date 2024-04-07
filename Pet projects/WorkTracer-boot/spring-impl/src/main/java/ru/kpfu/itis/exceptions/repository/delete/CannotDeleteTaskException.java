package ru.kpfu.itis.exceptions.repository.delete;

public class CannotDeleteTaskException extends CannotDeleteRepositoryException {

    public CannotDeleteTaskException() {
        super("Failed to delete task, try again");
    }

}
