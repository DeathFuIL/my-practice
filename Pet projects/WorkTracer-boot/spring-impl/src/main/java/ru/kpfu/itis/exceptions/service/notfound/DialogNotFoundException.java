package ru.kpfu.itis.exceptions.service.notfound;

import java.util.UUID;

public class DialogNotFoundException extends NotFoundServiceException {
    public DialogNotFoundException(UUID uuid) {
        super("Dialog with id = %s - not found");
    }
}
