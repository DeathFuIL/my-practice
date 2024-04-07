package ru.kpfu.itis.exceptions.repository.insert;

public class CannotInsertAccountException extends CannotInsertRepositoryException {

    public CannotInsertAccountException() {
        super("Failed to create account, try again");
    }

}
