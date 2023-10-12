package com.example.authspringsecurityapp.domain.product;

public record ProductResponseDTO(String id, String name, Integer preco) {
    public ProductResponseDTO(Product product) {
        this(product.getId(), product.getName(), product.getPrice());
    }
}
