package ru.kpfu.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.request.TaskRequest;
import ru.kpfu.itis.dto.response.TaskResponse;
import ru.kpfu.itis.exceptions.repository.delete.CannotDeleteTargetException;
import ru.kpfu.itis.exceptions.repository.delete.CannotDeleteTaskException;
import ru.kpfu.itis.exceptions.service.notfound.TaskNotFoundException;
import ru.kpfu.itis.mappers.TaskMapper;
import ru.kpfu.itis.models.TaskEntity;
import ru.kpfu.itis.repositories.AccountRepository;
import ru.kpfu.itis.repositories.TaskRepository;
import ru.kpfu.itis.services.TargetService;
import ru.kpfu.itis.services.TaskService;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final AccountRepository accountRepository;

    private final TaskRepository taskRepository;

    private final TaskMapper mapper;

    @Override
    public TaskResponse getById(UUID uuid) {
        TaskEntity taskEntity = taskRepository.findById(uuid)
                .orElseThrow(() -> new TaskNotFoundException(uuid));
        setAttributes(taskEntity);

        return mapper.toResponse(taskEntity);
    }

    @Override
    public Set<TaskResponse> getAll() {
        return taskRepository.findAll().get()
                .peek(this::setAttributes)
                .map(mapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public UUID create(TaskRequest request) {
        return taskRepository.create(
                mapper.toEntity(request)
        );
    }

    @Override
    public void update(UUID uuid, TaskRequest request) {
        taskRepository.update(uuid,
                mapper.toEntity(request)
        );
    }

    @Override
    public void delete(UUID uuid) {
        taskRepository.delete(uuid);
    }

    @Override
    public List<TaskEntity> getAllTasksByTargetId(UUID targetUUID) {
        return taskRepository.getAllByTargetId(targetUUID);
    }

    @Override
    public void deleteAllTasksByTargetId(UUID targetUUID) throws CannotDeleteTaskException {
        taskRepository.deleteAllByTargetId(targetUUID);
    }

    private void setAttributes(TaskEntity taskEntity) {
        taskEntity.setExecutors(accountRepository.getAllExecutorsByTaskId(taskEntity.getId()));
        taskEntity.setAssigner(accountRepository.getAssignerByTaskId(taskEntity.getId()));
    }
}
