package ru.kpfu.itis.exceptions.repository.insert;

public class CannotInsertDialogException extends CannotInsertRepositoryException {
    public CannotInsertDialogException() {
        super("Failed to create dialog, try again");
    }
}
