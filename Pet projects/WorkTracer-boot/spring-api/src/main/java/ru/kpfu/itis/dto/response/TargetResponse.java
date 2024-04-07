package ru.kpfu.itis.dto.response;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public record TargetResponse(UUID id,
                             String name,
                             String description,
                             Date dateOfCreating,
                             Date dateOfUpdating,
                             Date approximateDeadline,
                             Date actualDeadline,
                             List<TaskResponse> tasks,
                             List<AccountResponse> responsibles,
                             List<AccountResponse> executors,
                             List<AccountResponse> spectators) {
}
