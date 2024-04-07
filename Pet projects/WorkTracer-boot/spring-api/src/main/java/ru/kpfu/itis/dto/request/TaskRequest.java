package ru.kpfu.itis.dto.request;

import java.sql.Date;
import java.util.List;

public record TaskRequest(String name,
                          String description,
                          AccountRequest assigner,
                          List<AccountRequest> executors,
                          String status) {
}
