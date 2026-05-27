package com.taskflow.gateway_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private static final String SECRET =
            "taskflow-secret-key-taskflow-secret-key";

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET.getBytes()
            );

    public boolean isValid(String token) {

        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractSubject(String token) {

        return parseClaims(token)
                .getSubject();
    }

    private Claims parseClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}