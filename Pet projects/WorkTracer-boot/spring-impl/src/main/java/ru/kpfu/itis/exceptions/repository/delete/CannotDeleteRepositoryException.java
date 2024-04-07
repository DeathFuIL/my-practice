package ru.kpfu.itis.exceptions.repository.delete;

import org.springframework.http.HttpStatus;
import ru.kpfu.itis.exceptions.repository.RepositoryException;

public class CannotDeleteRepositoryException extends RepositoryException {

    public CannotDeleteRepositoryException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
