package ru.kpfu.itis.exceptions.repository.update;

import org.springframework.http.HttpStatus;
import ru.kpfu.itis.exceptions.repository.RepositoryException;

public class CannotUpdateRepositoryException extends RepositoryException {

    public CannotUpdateRepositoryException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
