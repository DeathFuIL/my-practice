package ru.kpfu.itis.dto.request;

import java.util.List;

public record DialogRequest(List<AccountRequest> accounts) {
}
