package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.TargetEntity;

import java.util.List;
import java.util.UUID;

public interface TargetRepository extends CrudRepository<TargetEntity> {

    void deleteAllResponsiblesByAccountId(UUID accountUUID);

    void deleteAllExecutorsByAccountId(UUID accountUUID);

    List<TargetEntity> getAllByAccountId(UUID accountUUID);

    void deleteAllSpectatorsByAccountId(UUID accountUUID);

    boolean doesTargetHaveParticipant(UUID targetUUID);

    void createTask(UUID taskUUID, UUID targetUUID);
}
