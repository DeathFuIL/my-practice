package ru.kpfu.itis.exceptions.service.notfound;

import java.util.UUID;

public class AddressNotFoundException extends NotFoundServiceException {

    public AddressNotFoundException(UUID uuid) {
        super("Address with id = %s - not found".formatted(uuid));
    }

}
