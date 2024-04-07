package ru.kpfu.itis.exceptions.repository.insert;

public class CannotInsertTargetException extends CannotInsertRepositoryException {

    public CannotInsertTargetException() {
        super("Failed to create target, try again");
    }

}
