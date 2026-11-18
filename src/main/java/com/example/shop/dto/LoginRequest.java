package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Données reçues lors de la connexion
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}