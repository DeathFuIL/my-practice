package ru.kpfu.itis.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.request.TaskRequest;
import ru.kpfu.itis.dto.response.TaskResponse;

import java.util.UUID;
import java.util.Set;

@Api(tags = "Tasks | Задачи")
@RequestMapping("/api/tasks/")
public interface TaskAPI {

    @ApiOperation(value = "Получение задачи", nickname = "task-get-information", response = TaskResponse.class)
    @GetMapping("/{task-id}")
    @ResponseStatus(HttpStatus.OK)
    TaskResponse getById(@PathVariable("task-id") UUID uuid);

    @ApiOperation(value = "Получение задач", nickname = "tasks-get-information", response = Set.class)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Set<TaskResponse> getAll();

    @ApiOperation(value = "Создание задачи", nickname = "task-create", response = UUID.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Задача создана", response = UUID.class),
            @ApiResponse(code = 400, message = "Ошибка валидации"),
            @ApiResponse(code = 500, message = "Серверная ошибка")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID create(@RequestBody TaskRequest taskRequest);

    @ApiOperation(value = "Внесение изменений в задачу", nickname = "task-update-information", response = void.class)
    @PatchMapping("/{task-id}")
    @ResponseStatus(HttpStatus.OK)
    void update(@PathVariable("task-id") UUID uuid, @RequestBody TaskRequest taskRequest);

    @ApiOperation(value = "Удаление задачи", nickname = "task-delete", response = TaskResponse.class)
    @DeleteMapping("/{task-id}")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable("task-id") UUID uuid);
}
