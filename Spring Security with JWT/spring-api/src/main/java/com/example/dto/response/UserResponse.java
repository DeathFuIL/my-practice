package com.example.dto.response;

import java.sql.Date;
import java.util.UUID;

public record UserResponse(UUID id,
                           String name,
                           String email,
                           String password,
                           Date birthday) {
}
