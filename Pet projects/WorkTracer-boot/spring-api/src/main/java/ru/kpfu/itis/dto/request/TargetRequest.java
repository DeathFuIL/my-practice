package ru.kpfu.itis.dto.request;

import java.sql.Date;
import java.util.List;

public record TargetRequest(String name,
                            String description,
                            Date approximateDeadline,
                            List<TaskRequest> tasks,
                            List<AccountRequest> responsibles,
                            List<AccountRequest> executors,
                            List<AccountRequest> spectators) {
}
