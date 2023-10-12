package com.example.authspringsecurityapp.domain.user;

public record RegisterDTO(String login, String password, UserRole role) { // passe login, password e role e receba um objeto RegisterDTO
}
