package com.example.security.provider;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAccessTokenProvider {

    @Value("${jwt.expiration.access.millis}")
    private long expirationAccessInMills;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String createAccessToken(String subject, Map<String, Object> data) {
        Date now = new Date();
        Date expiryDate = Date.from(Instant.now().plusMillis(expirationAccessInMills));
        Claims claims = Jwts.claims(data);
        Key secretKey = getSigningKey();

        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public Claims parseAccessToken(String token) {
        if (!isValidToken(token)) {
            return Jwts.claims(); //empty claims
        }

        Key secretKey = getSigningKey();
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

        return claims.getBody();
    }

    public boolean isValidToken(String token) {
        try {
            Key secretKey = getSigningKey();
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Key getSigningKey() {
        return new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA512");
    }

}
