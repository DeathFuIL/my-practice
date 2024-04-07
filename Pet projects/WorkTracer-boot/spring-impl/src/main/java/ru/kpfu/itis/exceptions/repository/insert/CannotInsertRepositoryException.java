package ru.kpfu.itis.exceptions.repository.insert;

import org.springframework.http.HttpStatus;
import ru.kpfu.itis.exceptions.repository.RepositoryException;

public class CannotInsertRepositoryException extends RepositoryException {

    public CannotInsertRepositoryException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
