package com.example.dto.request;

import java.sql.Date;
import java.util.UUID;

public record UserRequest(String name,
                          String email,
                          String password,
                          Date birthday) {
}
