package com.example.security.dto;


public record AuthenticationRequest(String email,
                                    String password) {
}
