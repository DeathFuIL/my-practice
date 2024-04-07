package ru.kpfu.itis.services;

import ru.kpfu.itis.dto.request.TargetRequest;
import ru.kpfu.itis.dto.request.TaskRequest;
import ru.kpfu.itis.dto.response.TargetResponse;

import java.util.UUID;

public interface TargetService extends Service<TargetRequest, TargetResponse> {

    void deleteAllTargetsByAccountId(UUID accountUUID);

    void createTask(TaskRequest taskRequest, UUID targetUUID);

}
