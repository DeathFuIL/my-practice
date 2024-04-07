package ru.kpfu.itis.exceptions.repository.delete;

public class CannotDeleteCompanyException extends CannotDeleteRepositoryException {

    public CannotDeleteCompanyException() {
        super("Failed to delete company, try again");
    }

}
