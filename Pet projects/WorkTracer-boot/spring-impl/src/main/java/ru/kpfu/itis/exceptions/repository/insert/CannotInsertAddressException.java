package ru.kpfu.itis.exceptions.repository.insert;

public class CannotInsertAddressException extends CannotInsertRepositoryException {

    public CannotInsertAddressException() {
        super("Failed to save address, try again");
    }

}
