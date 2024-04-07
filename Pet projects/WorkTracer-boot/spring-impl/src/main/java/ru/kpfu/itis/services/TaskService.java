package ru.kpfu.itis.services;

import ru.kpfu.itis.dto.request.TaskRequest;
import ru.kpfu.itis.dto.response.TaskResponse;
import ru.kpfu.itis.models.TaskEntity;

import java.util.List;
import java.util.UUID;

public interface TaskService extends Service<TaskRequest, TaskResponse> {

    List<TaskEntity> getAllTasksByTargetId(UUID targetUUID);

    void deleteAllTasksByTargetId(UUID targetUUID);
}
