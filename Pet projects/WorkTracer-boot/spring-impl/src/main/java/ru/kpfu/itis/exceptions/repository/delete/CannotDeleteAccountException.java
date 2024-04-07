package ru.kpfu.itis.exceptions.repository.delete;

public class CannotDeleteAccountException extends CannotDeleteRepositoryException {

    public CannotDeleteAccountException() {
        super("Failed to delete account, try again");
    }

}
