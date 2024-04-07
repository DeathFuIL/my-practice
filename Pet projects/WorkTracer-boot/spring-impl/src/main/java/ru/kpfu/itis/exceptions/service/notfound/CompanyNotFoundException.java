package ru.kpfu.itis.exceptions.service.notfound;

import java.util.UUID;

public class CompanyNotFoundException extends NotFoundServiceException {

    public CompanyNotFoundException(UUID uuid) {
        super("Company with id = %s - not found".formatted(uuid));
    }

}
