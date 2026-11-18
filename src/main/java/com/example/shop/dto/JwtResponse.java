package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// Réponse envoyée après une connexion réussie contenant le token
@Getter @Setter @AllArgsConstructor
public class JwtResponse {
    private String token;
}