package com.example.authspringsecurityapp.domain.product;

import java.math.BigDecimal;

public record ProductResponseDTO(String id, String name, BigDecimal preco) {
    public ProductResponseDTO(Product product) {
        this(product.getId(), product.getName(), product.getPrice());
    }
}
