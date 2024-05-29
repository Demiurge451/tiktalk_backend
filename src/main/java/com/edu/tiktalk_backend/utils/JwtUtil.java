package com.edu.tiktalk_backend.utils;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtUtil {
    public UUID getIdFromToken(Jwt jwt) {
        return UUID.fromString(jwt.getClaim(JwtClaimNames.SUB));
    }
}
