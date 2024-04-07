package ru.kpfu.itis.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.request.AccountRequest;
import ru.kpfu.itis.dto.response.AccountResponse;

import java.util.UUID;
import java.util.Set;

@Api(tags = "Users | Пользователи")
@RequestMapping("/api/accounts/")
public interface AccountAPI {

    @ApiOperation(value = "Получение аккаунтa", nickname = "account-get-information", response = AccountResponse.class)
    @GetMapping("/{account-id}")
    @ResponseStatus(HttpStatus.OK)
    AccountResponse getById(@PathVariable("account-id") UUID uuid);

    @ApiOperation(value = "Получение аккаунтов", nickname = "accounts-get-information", response = Set.class)
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    Set<AccountResponse> getAll();

    @ApiOperation(value = "Создание аккаунта", nickname = "account-create", response = UUID.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Аккаунт создан", response = UUID.class),
            @ApiResponse(code = 400, message = "Ошибка валидации"),
            @ApiResponse(code = 500, message = "Серверная ошибка")
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    UUID create(@RequestBody AccountRequest accountRequest);

    @ApiOperation(value = "Внесение изменений в аккаунт", nickname = "account-update-information", response = void.class)
    @PatchMapping("/{account-id}")
    @ResponseStatus(HttpStatus.OK)
    void updateAccount(@PathVariable("account-id") UUID uuid, @RequestBody AccountRequest accountRequest);

    @ApiOperation(value = "Удаление аккаунта", nickname = "account-delete", response = void.class)
    @DeleteMapping("/{account-id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteAccount(@PathVariable("account-id") UUID uuid);
}
