package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.MessageEntity;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends CrudRepository<MessageEntity> {

    void deleteAllByDialogId(UUID dialogUUID);

    List<MessageEntity> getAllByDialogId(UUID dialogUUID);

    void setMessageTo(UUID messageUUID, UUID accountUUID);
}
