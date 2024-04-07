package ru.kpfu.itis.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.api.TaskAPI;
import ru.kpfu.itis.dto.request.TaskRequest;
import ru.kpfu.itis.dto.response.TaskResponse;
import ru.kpfu.itis.services.TaskService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TaskController implements TaskAPI {

    private final TaskService service;

    @Override
    public TaskResponse getById(UUID uuid) {
        return service.getById(uuid);
    }

    @Override
    public Set<TaskResponse> getAll() {
        return service.getAll();
    }

    @Override
    public UUID create(TaskRequest taskRequest) {
        return service.create(taskRequest);
    }

    @Override
    public void update(UUID uuid, TaskRequest taskRequest) {
        service.update(uuid, taskRequest);
    }

    @Override
    public void delete(UUID uuid) {
        service.delete(uuid);
    }
}
