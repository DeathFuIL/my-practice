package ru.kpfu.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.request.TargetRequest;
import ru.kpfu.itis.dto.request.TaskRequest;
import ru.kpfu.itis.dto.response.TargetResponse;
import ru.kpfu.itis.exceptions.repository.delete.CannotDeleteTargetException;
import ru.kpfu.itis.exceptions.repository.delete.CannotDeleteTaskException;
import ru.kpfu.itis.exceptions.service.notfound.TargetNotFoundException;
import ru.kpfu.itis.mappers.TargetMapper;
import ru.kpfu.itis.models.TargetEntity;
import ru.kpfu.itis.repositories.TargetRepository;
import ru.kpfu.itis.services.AccountService;
import ru.kpfu.itis.services.TargetService;
import ru.kpfu.itis.services.TaskService;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class TargetServiceImpl implements TargetService {

    private final AccountService accountService;

    private final TaskService taskService;

    private final TargetRepository targetRepository;

    private final TargetMapper mapper;

    @Override
    public TargetResponse getById(UUID uuid) {
        TargetEntity targetEntity = targetRepository.findById(uuid)
                .orElseThrow(() -> new TargetNotFoundException(uuid));
        setAttributes(targetEntity);

        return mapper.toResponse(targetEntity);
    }

    @Override
    public Set<TargetResponse> getAll() {
        return targetRepository.getAll().stream()
                .peek(this::setAttributes)
                .map(mapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public UUID create(TargetRequest request) {
        return targetRepository.create(
                mapper.toEntity(request)
        );
    }

    @Override
    public void update(UUID uuid, TargetRequest request) {
        targetRepository.update(uuid,
                mapper.toEntity(request)
        );
    }

    @Override
    public void delete(UUID uuid) {
        taskService.deleteAllTasksByTargetId(uuid);
        targetRepository.delete(uuid);
    }

    @Override
    public void deleteAllTargetsByAccountId(UUID accountUUID) throws CannotDeleteTargetException {
        List<TargetEntity> targetsThatTheAccountParticipated = targetRepository.getAllByAccountId(accountUUID);

        //delete all references to our account from many-to-many tables,
        //but we have to get these targets before deleting (look above)
        targetRepository.deleteAllResponsiblesByAccountId(accountUUID);
        targetRepository.deleteAllExecutorsByAccountId(accountUUID);
        targetRepository.deleteAllSpectatorsByAccountId(accountUUID);

        try {
            targetsThatTheAccountParticipated.forEach(target -> {
                if (!targetRepository.doesTargetHaveParticipant(target.getId())) {
                    delete(target.getId());
                }
            });
        } catch (CannotDeleteTaskException e) {
            throw new CannotDeleteTargetException();
        }
    }

    @Override
    public void createTask(TaskRequest taskRequest, UUID targetUUID) {
        UUID taskUUID = taskService.create(taskRequest);
        targetRepository.createTask(taskUUID, targetUUID);
    }


    private void setAttributes(TargetEntity targetEntity) {
        targetEntity.setSpectators(accountService.getAllSpectatorsByTargetId(targetEntity.getId()));
        targetEntity.setResponsibles(accountService.getAllResponsiblesByTargetId(targetEntity.getId()));
        targetEntity.setExecutors(accountService.getAllExecutorsByTargetId(targetEntity.getId()));
        targetEntity.setTasks(taskService.getAllTasksByTargetId(targetEntity.getId()));
    }
}
