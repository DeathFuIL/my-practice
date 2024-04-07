package ru.kpfu.itis.exceptions.repository.update;

public class CannotUpdateCompanyException extends CannotUpdateRepositoryException {

    public CannotUpdateCompanyException() {
        super("Failed to update company information, try again");
    }
}
