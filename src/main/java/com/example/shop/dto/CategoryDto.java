package com.example.shop.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(Long id, @NotBlank String name) {}