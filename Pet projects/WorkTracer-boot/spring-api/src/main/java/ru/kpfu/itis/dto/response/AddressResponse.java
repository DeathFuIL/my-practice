package ru.kpfu.itis.dto.response;

import java.util.UUID;

public record AddressResponse(UUID id,
                              String city,
                              String state,
                              String country,
                              int postalCode) {
}
