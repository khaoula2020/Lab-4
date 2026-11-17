package com.example.shop.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

// Classe utilitaire : crée et vérifie les tokens JWT
@Component
public class JwtUtil {

    private final String jwtSecret = "MySuperSecretKeyForJwtSigningDontShare"; // à externaliser
    private final long jwtExpirationMs = 86400000; // durée de validité : 24h
    private final Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    // Crée un token contenant le nom d'utilisateur et son rôle
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Vérifie que le token est bien signé avec notre clé et qu'il n'est pas expiré
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Lit le nom d'utilisateur écrit dans le token
    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}