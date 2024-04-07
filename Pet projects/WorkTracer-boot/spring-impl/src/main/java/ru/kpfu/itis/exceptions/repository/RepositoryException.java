package ru.kpfu.itis.exceptions.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class RepositoryException extends RuntimeException {

    private final HttpStatus status;

    public RepositoryException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
