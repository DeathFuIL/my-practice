package ru.kpfu.itis.exceptions.service.notfound;

import java.util.UUID;

public class TaskNotFoundException extends NotFoundServiceException {

    public TaskNotFoundException(UUID uuid) {
        super("Task with id = %s - not found".formatted(uuid));
    }

}
