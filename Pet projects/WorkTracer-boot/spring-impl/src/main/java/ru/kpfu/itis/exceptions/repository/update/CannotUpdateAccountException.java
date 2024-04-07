package ru.kpfu.itis.exceptions.repository.update;

public class CannotUpdateAccountException extends CannotUpdateRepositoryException {

    public CannotUpdateAccountException() {
        super("Failed to update account information, try again");
    }
}
