package com.matchup.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    // Generate a secure secret key for HS256 algorithm
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("This-secret-willbe-stored-somewhere-else".getBytes());
    // Token validity duration (e.g., 10 hours)
    private static final long EXPIRATION_TIME =  5 * 60 * 1000; // 5 minuts in in milliseconds (small for testing)

    /**
     * Generate a JWT token for the given user details.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Create a JWT token with the given claims and subject (username).
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims) // Use the non-deprecated claims method
                .subject(subject) // Username or email
                .issuedAt(new Date(System.currentTimeMillis())) // Issued at
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiration time
                .signWith(SECRET_KEY) // Sign with the secret key
                .compact(); // Build the token
    }

    /**
     * Extract the username (subject) from the JWT token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract the expiration date from the JWT token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract a specific claim from the JWT token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from the JWT token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY) // Verify the token with the secret key
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Check if the JWT token is expired.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validate the JWT token for the given user details.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}