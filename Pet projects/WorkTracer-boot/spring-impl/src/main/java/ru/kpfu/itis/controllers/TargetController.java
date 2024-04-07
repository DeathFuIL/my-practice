package ru.kpfu.itis.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.api.TargetAPI;
import ru.kpfu.itis.dto.request.TargetRequest;
import ru.kpfu.itis.dto.response.TargetResponse;
import ru.kpfu.itis.services.TargetService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TargetController implements TargetAPI {

    private final TargetService targetService;

    @Override
    public TargetResponse getById(UUID uuid) {
        return targetService.getById(uuid);
    }

    @Override
    public Set<TargetResponse> getAll() {
        return targetService.getAll();
    }

    @Override
    public UUID create(TargetRequest targetRequest) {
        return targetService.create(targetRequest);
    }

    @Override
    public void update(UUID uuid, TargetRequest targetRequest) {
        targetService.update(uuid, targetRequest);
    }

    @Override
    public void delete(UUID uuid) {
        targetService.delete(uuid);
    }
}
