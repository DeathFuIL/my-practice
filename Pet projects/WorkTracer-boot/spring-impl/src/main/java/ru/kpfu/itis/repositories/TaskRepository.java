package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.TaskEntity;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends CrudRepository<TaskEntity> {

    List<TaskEntity> getAllByTargetId(UUID targetUUID);

    void deleteAllByTargetId(UUID targetUUID);
}
