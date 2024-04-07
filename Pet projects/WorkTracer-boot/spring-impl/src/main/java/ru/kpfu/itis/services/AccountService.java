package ru.kpfu.itis.services;

import ru.kpfu.itis.dto.request.AccountRequest;
import ru.kpfu.itis.dto.response.AccountResponse;
import ru.kpfu.itis.models.AccountEntity;

import java.util.List;
import java.util.UUID;

public interface AccountService extends Service<AccountRequest, AccountResponse> {

    List<AccountResponse> getFriendsById(UUID uuid);

    List<AccountEntity> getAllExecutorsByTargetId(UUID targetUUID);

    List<AccountEntity> getAllSpectatorsByTargetId(UUID targetUUID);

    List<AccountEntity> getAllResponsiblesByTargetId(UUID targetUUID);

    List<AccountEntity> getAllExecutorsByTaskId(UUID taskUUID);

    AccountEntity getAssignerByTaskId(UUID taskUUID);

    List<AccountEntity> getAllAccountsByCompanyId(UUID companyUUID);

    List<AccountEntity> getAllAccountsByDialogId(UUID dialogUUID);

    AccountEntity getByEmail(String email);
}
