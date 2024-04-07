package ru.kpfu.itis.dto.response;

import java.util.List;
import java.util.UUID;

public record DialogResponse(UUID id,
                             List<AccountResponse> accounts,
                             List<MessageResponse> messages) {
}
