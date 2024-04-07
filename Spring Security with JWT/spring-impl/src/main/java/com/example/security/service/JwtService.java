package com.example.security.service;

import com.example.security.provider.JwtAccessTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtAccessTokenProvider jwtAccessTokenProvider;

    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = extractExpiration(token);
        return expirationDate.before(new Date());
    }

    private <R> R extractClaim(String token, Function<Claims, R> claimsResolver) {
        Claims claims = jwtAccessTokenProvider.parseAccessToken(token);
        return claimsResolver.apply(claims);
    }

}
