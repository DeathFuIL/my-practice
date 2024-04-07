package ru.kpfu.itis.exceptions.service.notfound;

import java.util.UUID;

public class MessageNotFoundException extends NotFoundServiceException {
    public MessageNotFoundException(UUID uuid) {
        super("Message with id = %s - not found");
    }
}
