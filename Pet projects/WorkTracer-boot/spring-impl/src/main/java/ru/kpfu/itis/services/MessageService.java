package ru.kpfu.itis.services;

import ru.kpfu.itis.dto.request.MessageRequest;
import ru.kpfu.itis.dto.response.MessageResponse;
import ru.kpfu.itis.models.MessageEntity;

import java.util.List;
import java.util.UUID;

public interface MessageService extends Service<MessageRequest, MessageResponse> {

    void deleteAllMessagesByDialogId(UUID dialogUUID);

    List<MessageEntity> getAllMessagesByDialogId(UUID dialogUUID);

    void setMessage(MessageRequest message, String accountEmail);
}
