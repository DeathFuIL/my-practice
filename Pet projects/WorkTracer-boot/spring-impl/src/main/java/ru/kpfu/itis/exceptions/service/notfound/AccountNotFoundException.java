package ru.kpfu.itis.exceptions.service.notfound;

import java.util.UUID;

public class AccountNotFoundException extends NotFoundServiceException {

    public AccountNotFoundException(UUID uuid) {
        super("Account with id = %s - not found".formatted(uuid));
    }

}
