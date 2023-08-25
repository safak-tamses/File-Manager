package com.example.etstur_java_backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        jwtService = new JwtService();
        userDetails = User.builder()
                .username("testuser")
                .password("password")
                .roles("USER")
                .build();
    }

    @Test
    public void testGenerateAndValidateToken() {
        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    public void testExtractUsernameFromToken() {
        String token = jwtService.generateToken(userDetails);

        String extractedUsername = jwtService.extractUsername(token);

        assertEquals(userDetails.getUsername(), extractedUsername);
    }

    @Test
    public void testExtractClaimFromToken() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("customClaim", "customValue");

        String token = jwtService.generateToken(extraClaims, userDetails);

        String extractedCustomClaim = jwtService.extractClaim(token, claims -> {
            return claims.get("customClaim", String.class);
        });

        assertEquals("customValue", extractedCustomClaim);
    }

    @Test
    public void testExtractExpirationFromToken() {
        String token = jwtService.generateToken(userDetails);

        Date expiration = jwtService.extractExpiration(token);

        assertTrue(expiration.after(new Date()));
    }

    @Test
    public void testExtractAllClaimsFromToken() {
        String token = jwtService.generateToken(userDetails);

        Claims claims = jwtService.extractAllClaims(token);

        assertEquals(userDetails.getUsername(), claims.getSubject());
    }

    @Test
    public void testInvalidToken() {

        String invalidToken = "invalidTokenFormat";

        assertThrows(MalformedJwtException.class, () -> jwtService.isTokenValid(invalidToken, userDetails));
    }
}
