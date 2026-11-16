package com.example.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDto(Long id, @NotBlank String name, Double price, @NotNull Long categoryId) {}