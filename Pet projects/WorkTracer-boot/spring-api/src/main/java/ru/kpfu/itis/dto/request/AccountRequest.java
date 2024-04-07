package ru.kpfu.itis.dto.request;

import java.sql.Date;
import java.util.UUID;

public record AccountRequest(String name,
                             String surname,
                             Date birthday,
                             String email,
                             AddressRequest addressRequest,
                             String profilePhoto,
                             String phoneNumber,
                             String position,
                             String password) {
}
