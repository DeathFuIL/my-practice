package ru.kpfu.itis.exceptions.service.notfound;

import java.util.UUID;

public class TargetNotFoundException extends NotFoundServiceException {

    public TargetNotFoundException(UUID uuid) {
        super("Target with id = %s - not found".formatted(uuid));
    }

}
