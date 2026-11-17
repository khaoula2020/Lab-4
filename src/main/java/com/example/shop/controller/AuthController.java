package com.example.shop.controller;

import com.example.shop.dto.RegisterRequest;
import com.example.shop.entity.User;
import com.example.shop.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    // Inscription : POST /api/auth/register
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {
        // 1. Refuser si le nom d'utilisateur existe déjà
        if (userRepo.findByUsername(req.getUsername()).isPresent()) {
            return "Username already exists";
        }
        // 2. Créer l'utilisateur, avec le mot de passe haché
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole(req.getRole().toUpperCase());
        // 3. Enregistrer en base
        userRepo.save(user);
        return "User registered successfully";
    }
}