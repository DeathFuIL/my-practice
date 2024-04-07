package ru.kpfu.itis.dto.request;

import java.sql.Date;
import java.util.UUID;

public record CompanyRequest(String name,
                             Date foundingDate,
                             String email,
                             String industry,
                             String phoneNumber,
                             AddressRequest addressRequest
                             ) {
}
