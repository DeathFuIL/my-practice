package ru.kpfu.itis.exceptions.service.notfound;

import org.springframework.http.HttpStatus;
import ru.kpfu.itis.exceptions.service.ServiceException;

public class NotFoundServiceException extends ServiceException {

    public NotFoundServiceException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

}
