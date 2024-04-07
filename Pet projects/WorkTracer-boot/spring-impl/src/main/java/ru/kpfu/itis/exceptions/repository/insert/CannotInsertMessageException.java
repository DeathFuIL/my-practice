package ru.kpfu.itis.exceptions.repository.insert;

public class CannotInsertMessageException extends CannotInsertRepositoryException {
    public CannotInsertMessageException() {
        super("Failed to create message, try again");
    }
}
