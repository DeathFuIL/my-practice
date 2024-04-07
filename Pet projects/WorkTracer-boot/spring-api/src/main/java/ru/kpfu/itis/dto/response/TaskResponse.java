package ru.kpfu.itis.dto.response;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public record TaskResponse(UUID id,
                           String name,
                           String description,
                           Date dateOfCreating,
                           Date dateOfUpdating,
                           Date endDate,
                           Date dateOfApproval,
                           String status,
                           List<AccountResponse> executors,
                           AccountResponse assigner) {
}
