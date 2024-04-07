package com.example.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

public record AuthenticationResponse(String accessToken,
                                     String refreshToken) {
}
