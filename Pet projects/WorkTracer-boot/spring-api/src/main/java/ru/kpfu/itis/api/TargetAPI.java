package ru.kpfu.itis.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.request.TargetRequest;
import ru.kpfu.itis.dto.response.TargetResponse;

import java.util.UUID;
import java.util.Set;

@Api(tags = "Targets | Цели")
@RequestMapping("/api/targets/")
public interface TargetAPI {

    @ApiOperation(value = "Получение цели", nickname = "target-get-information", response = TargetResponse.class)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TargetResponse getById(@PathVariable("id") UUID uuid);

    @ApiOperation(value = "Получение целей", nickname = "targets-get-information", response = Set.class)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Set<TargetResponse> getAll();

    @ApiOperation(value = "Создание цели", nickname = "target-create", response = UUID.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Цель создана", response = UUID.class),
            @ApiResponse(code = 400, message = "Ошибка валидации"),
            @ApiResponse(code = 500, message = "Серверная ошибка")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID create(@RequestBody TargetRequest targetRequest);

    @ApiOperation(value = "Внесение изменений в цель", nickname = "target-update-information", response = void.class)
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void update(@PathVariable("id") UUID uuid, @RequestBody TargetRequest targetRequest);

    @ApiOperation(value = "Удаление цели", nickname = "target-delete", response = TargetResponse.class)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable("id") UUID uuid);
}
