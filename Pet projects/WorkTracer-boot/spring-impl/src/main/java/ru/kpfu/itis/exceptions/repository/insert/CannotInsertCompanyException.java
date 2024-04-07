package ru.kpfu.itis.exceptions.repository.insert;

public class CannotInsertCompanyException extends CannotInsertRepositoryException {

    public CannotInsertCompanyException() {
        super("Failed to create company, try again");
    }

}
