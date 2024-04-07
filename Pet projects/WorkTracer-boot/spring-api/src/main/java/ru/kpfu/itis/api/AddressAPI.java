package ru.kpfu.itis.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.request.AddressRequest;
import ru.kpfu.itis.dto.response.AddressResponse;

import java.util.Set;
import java.util.UUID;

@Api(tags = "Addresses | Адреса")
@RequestMapping("/api/addresses")
public interface AddressAPI {

    @ApiOperation(value = "Получение адреса", nickname = "address-get-information", response = AddressResponse.class)
    @GetMapping("/{address-id}")
    @ResponseStatus(HttpStatus.OK)
    AddressResponse getById(@PathVariable("address-id") UUID uuid);

    @ApiOperation(value = "Получение адресов", nickname = "addresses-get-information", response = Set.class)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Set<AddressResponse> getAll();

    @ApiOperation(value = "Создание адреса", nickname = "address-create", response = UUID.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Адрес создан", response = UUID.class),
            @ApiResponse(code = 400, message = "Ошибка валидации"),
            @ApiResponse(code = 500, message = "Серверная ошибка")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID create(@RequestBody AddressRequest addressRequest);

}
