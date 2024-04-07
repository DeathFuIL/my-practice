package ru.kpfu.itis.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.request.CompanyRequest;
import ru.kpfu.itis.dto.response.CompanyResponse;

import java.util.Set;
import java.util.UUID;

@Api(tags = "Companies | Компании")
@RequestMapping("/api/companies")
public interface CompanyAPI {

    @ApiOperation(value = "Получение компании", nickname = "company-get-information", response = CompanyResponse.class)
    @GetMapping("/{company-id}")
    @ResponseStatus(HttpStatus.OK)
    CompanyResponse getById(@PathVariable("company-id") UUID uuid);

    @ApiOperation(value = "Получение компаний", nickname = "companies-get-information", response = Set.class)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Set<CompanyResponse> getAll();

    @ApiOperation(value = "Создание компании", nickname = "company-create", response = UUID.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Комания создана", response = UUID.class),
            @ApiResponse(code = 400, message = "Ошибка валидации"),
            @ApiResponse(code = 500, message = "Серверная ошибка")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID create(@RequestBody CompanyRequest companyRequest);

    @ApiOperation(value = "Внесение изменений в компанию", nickname = "company-update", response = void.class)
    @PatchMapping("/{company-id}")
    @ResponseStatus(HttpStatus.OK)
    void update(@PathVariable("company-id") UUID uuid, @RequestBody CompanyRequest companyRequest);

    @ApiOperation(value = "Удаление компании", nickname = "company-delete", response = void.class)
    @DeleteMapping("/{company-id}")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable("company-id") UUID uuid);

}
