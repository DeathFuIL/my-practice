package ru.kpfu.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.request.MessageRequest;
import ru.kpfu.itis.dto.response.MessageResponse;
import ru.kpfu.itis.exceptions.repository.insert.CannotInsertMessageException;
import ru.kpfu.itis.exceptions.service.notfound.MessageNotFoundException;
import ru.kpfu.itis.mappers.MessageMapper;
import ru.kpfu.itis.models.AccountEntity;
import ru.kpfu.itis.models.MessageEntity;
import ru.kpfu.itis.repositories.MessageRepository;
import ru.kpfu.itis.services.AccountService;
import ru.kpfu.itis.services.MessageService;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final AccountService accountService;

    private final MessageRepository messageRepository;

    private final MessageMapper mapper;

    @Override
    public MessageResponse getById(UUID uuid) {
        return mapper.toResponse(
                messageRepository.findById(uuid)
                    .orElseThrow(() -> new MessageNotFoundException(uuid))
        );
    }

    @Override
    public Set<MessageResponse> getAll() {
        return messageRepository.getAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public UUID create(MessageRequest request) {
        return messageRepository.create(mapper.toEntity(request));
    }

    @Override
    public void update(UUID uuid, MessageRequest request) {
        messageRepository.update(uuid, mapper.toEntity(request));
    }

    @Override
    public void delete(UUID uuid) {
        messageRepository.delete(uuid);
    }

    @Override
    public void deleteAllMessagesByDialogId(UUID dialogUUID) {
        messageRepository.deleteAllByDialogId(dialogUUID);
    }

    @Override
    public List<MessageEntity> getAllMessagesByDialogId(UUID dialogUUID) {
        return messageRepository.getAllByDialogId(dialogUUID);
    }

    @Override
    public void setMessage(MessageRequest message, String accountEmail) {
        UUID messageUUID = create(message);
        AccountEntity account = accountService.getByEmail(accountEmail);
        if (Objects.isNull(account)) {
            throw new CannotInsertMessageException();
        }
        messageRepository.setMessageTo(messageUUID, account.getId());
    }
}
