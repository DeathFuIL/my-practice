package ru.kpfu.itis.dto.request;

import java.util.UUID;

public record AddressRequest(String city,
                             String state,
                             String country,
                             int postalCode) {
}
