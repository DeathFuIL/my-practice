package ru.kpfu.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.request.DialogRequest;
import ru.kpfu.itis.dto.response.DialogResponse;
import ru.kpfu.itis.mappers.DialogMapper;
import ru.kpfu.itis.models.DialogEntity;
import ru.kpfu.itis.repositories.DialogRepository;
import ru.kpfu.itis.services.AccountService;
import ru.kpfu.itis.services.DialogService;
import ru.kpfu.itis.services.MessageService;

import java.util.Set;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class DialogServiceImpl implements DialogService {

    private final DialogRepository dialogRepository;

    private final MessageService messageService;

    private final AccountService accountService;

    private final DialogMapper mapper;

    @Override
    public DialogResponse getById(UUID uuid) {
        return null;
    }

    @Override
    public Set<DialogResponse> getAll() {
        return null;
    }

    @Override
    public UUID create(DialogRequest request) {
        return null;
    }

    @Override
    public void update(UUID uuid, DialogRequest request) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    private void setAttributes(DialogEntity dialogEntity) {
        dialogEntity.setAccounts(accountService.getAllAccountsByDialogId(dialogEntity.getId()));
        dialogEntity.setMessages(messageService.getAllMessagesByDialogId(dialogEntity.getId()));
    }
}
