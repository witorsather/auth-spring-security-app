package com.example.authspringsecurityapp.domain.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank
        String name,
        @NotNull
        BigDecimal price
) {
}
