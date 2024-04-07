package ru.kpfu.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.request.CompanyRequest;
import ru.kpfu.itis.dto.response.CompanyResponse;
import ru.kpfu.itis.exceptions.repository.delete.CannotDeleteAccountException;
import ru.kpfu.itis.exceptions.repository.delete.CannotDeleteCompanyException;
import ru.kpfu.itis.exceptions.service.notfound.CompanyNotFoundException;
import ru.kpfu.itis.mappers.CompanyMapper;
import ru.kpfu.itis.models.AccountEntity;
import ru.kpfu.itis.repositories.CompanyRepository;
import ru.kpfu.itis.services.AccountService;
import ru.kpfu.itis.services.CompanyService;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {
    
    private final AccountService accountService;

    private final CompanyRepository companyRepository;

    private final CompanyMapper mapper;

    @Override
    public CompanyResponse getById(UUID uuid) {
        return mapper.toResponse(
                companyRepository.findById(uuid)
                        .orElseThrow(() -> new CompanyNotFoundException(uuid))
        );
    }

    @Override
    public Set<CompanyResponse> getAll() {
        return companyRepository.getAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public UUID create(CompanyRequest request) {
        return companyRepository.create(
                mapper.toEntity(request)
        );
    }

    @Override
    public void update(UUID uuid, CompanyRequest request) {
        companyRepository.update(uuid,
                mapper.toEntity(request)
        );
    }

    @Override
    public void delete(UUID uuid) {
        List<AccountEntity> accountThatBelongsToCompany = accountService.getAllAccountsByCompanyId(uuid);
        try {
            accountThatBelongsToCompany.forEach(account -> accountService.delete(account.getId()));
        } catch (CannotDeleteAccountException e) {
            throw new CannotDeleteCompanyException();
        }
        companyRepository.delete(uuid);
    }
}
