package ru.kpfu.itis.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.api.CompanyAPI;
import ru.kpfu.itis.dto.request.CompanyRequest;
import ru.kpfu.itis.dto.response.CompanyResponse;
import ru.kpfu.itis.services.CompanyService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CompanyController implements CompanyAPI {

    private final CompanyService service;

    @Override
    public CompanyResponse getById(UUID uuid) {
        return service.getById(uuid);
    }

    @Override
    public Set<CompanyResponse> getAll() {
        return service.getAll();
    }

    @Override
    public UUID create(CompanyRequest companyRequest) {
        return service.create(companyRequest);
    }

    @Override
    public void update(UUID uuid, CompanyRequest companyRequest) {
        service.update(uuid, companyRequest);
    }

    @Override
    public void delete(UUID uuid) {
        service.delete(uuid);
    }
}
