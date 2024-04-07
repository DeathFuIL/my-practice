package ru.kpfu.itis.dto.response;

import java.sql.Date;
import java.util.UUID;

public record CompanyResponse(UUID id,
                              String name,
                              Date foundingDate,
                              String email,
                              String industry,
                              String phoneNumber,
                              AddressResponse addressResponse
                             ) {
}
