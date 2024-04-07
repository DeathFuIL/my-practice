package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.AccountEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends CrudRepository<AccountEntity> {

    List<AccountEntity> getFriendsById(UUID uuid);

    List<AccountEntity> getAllExecutorsByTargetId(UUID targetUUID);

    List<AccountEntity> getAllResponsiblesByTargetId(UUID targetUUID);

    List<AccountEntity> getAllSpectatorsByTargetId(UUID targetUUID);

    List<AccountEntity> getAllExecutorsByTaskId(UUID taskUUID);

    AccountEntity getAssignerByTaskId(UUID taskUUID);

    List<AccountEntity> getAllByCompanyId(UUID companyUUID);


    List<AccountEntity> getAllByDialogId(UUID dialogUUID);

    AccountEntity getByEmail(String email);
}
