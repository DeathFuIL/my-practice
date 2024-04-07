package ru.kpfu.itis.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.api.AccountAPI;
import ru.kpfu.itis.dto.request.AccountRequest;
import ru.kpfu.itis.dto.response.AccountResponse;
import ru.kpfu.itis.services.AccountService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountAPI {

    private final AccountService service;

    @Override
    public AccountResponse getById(UUID uuid) {
        return service.getById(uuid);
    }

    @Override
    public Set<AccountResponse> getAll() {
        return service.getAll();
    }

    @Override
    public UUID create(AccountRequest accountRequest) {
        return service.create(accountRequest);
    }

    @Override
    public void updateAccount(UUID uuid, AccountRequest accountRequest) {
        service.update(uuid, accountRequest);
    }

    @Override
    public void deleteAccount(UUID uuid) {
        service.delete(uuid);
    }
}
