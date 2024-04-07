package ru.kpfu.itis.dto.response;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public record AccountResponse(UUID id,
                              String name,
                              String surname,
                              Date birthday,
                              String email,
                              AddressResponse address,
                              String profilePhoto,
                              String phoneNumber,
                              String position,
                              Date dateOfCreating,
                              String password,
                              List<AccountResponse> friends
                              ) {
}
