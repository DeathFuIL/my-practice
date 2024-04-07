package ru.kpfu.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.request.AccountRequest;
import ru.kpfu.itis.dto.response.AccountResponse;
import ru.kpfu.itis.exceptions.repository.delete.CannotDeleteAccountException;
import ru.kpfu.itis.exceptions.repository.delete.CannotDeleteTargetException;
import ru.kpfu.itis.exceptions.service.notfound.AccountNotFoundException;
import ru.kpfu.itis.mappers.AccountMapper;
import ru.kpfu.itis.models.AccountEntity;
import ru.kpfu.itis.repositories.AccountRepository;
import ru.kpfu.itis.repositories.TargetRepository;
import ru.kpfu.itis.services.AccountService;
import ru.kpfu.itis.services.AddressService;
import ru.kpfu.itis.services.TargetService;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final TargetService targetService;

    private final AccountRepository accountRepository;

    private final AccountMapper mapper;

    private final AddressService addressService;

    @Override
    public AccountResponse getById(UUID uuid) {
        return mapper.toResponse(
                accountRepository.findById(uuid)
                        .orElseThrow(() -> new AccountNotFoundException(uuid))
        );
    }

    @Override
    public Set<AccountResponse> getAll() {
        return accountRepository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public UUID create(AccountRequest request) {
        UUID addressUUID = addressService.create(request.addressRequest());
        AccountEntity accountEntity = mapper.toEntity(request);
        accountEntity.setDateOfCreating(new Date(System.currentTimeMillis()));
        accountEntity.getAddress().setId(addressUUID);
        return accountRepository.create(accountEntity);
    }

    @Override
    public void update(UUID uuid, AccountRequest request) {
        accountRepository.update(uuid, mapper.toEntity(request));
    }

    @Override
    public void delete(UUID uuid) throws CannotDeleteAccountException {
        try {
            targetService.deleteAllTargetsByAccountId(uuid); //delete all account's targets
        } catch (CannotDeleteTargetException e) {
            throw new CannotDeleteAccountException();
        }
        accountRepository.delete(uuid);
    }

    @Override
    public List<AccountResponse> getFriendsById(UUID uuid) {
        return accountRepository.getFriendsById(uuid).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<AccountEntity> getAllExecutorsByTargetId(UUID targetUUID) {
        return accountRepository.getAllExecutorsByTargetId(targetUUID);
    }

    @Override
    public List<AccountEntity> getAllSpectatorsByTargetId(UUID targetUUID) {
        return accountRepository.getAllSpectatorsByTargetId(targetUUID);
    }

    @Override
    public List<AccountEntity> getAllResponsiblesByTargetId(UUID targetUUID) {
        return accountRepository.getAllResponsiblesByTargetId(targetUUID);
    }

    @Override
    public List<AccountEntity> getAllExecutorsByTaskId(UUID taskUUID) {
        return accountRepository.getAllExecutorsByTaskId(taskUUID);
    }

    @Override
    public AccountEntity getAssignerByTaskId(UUID taskUUID) {
        return accountRepository.getAssignerByTaskId(taskUUID);
    }

    @Override
    public List<AccountEntity> getAllAccountsByCompanyId(UUID companyUUID) {
        return accountRepository.getAllByCompanyId(companyUUID);
    }

    @Override
    public List<AccountEntity> getAllAccountsByDialogId(UUID dialogUUID) {
        return accountRepository.getAllByDialogId(dialogUUID);
    }

    @Override
    public AccountEntity getByEmail(String email) {
        return accountRepository.getByEmail(email);
    }

}
