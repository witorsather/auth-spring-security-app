package com.example.authspringsecurityapp.domain.user;

public record AuthenticationDTO(String login, String password) { // passe login e password e receba um objeto AuthenticationDTO
    // construtor, getters, toString, criados automaticamente pelo record (tipo de dado a partir do java 16)
}
