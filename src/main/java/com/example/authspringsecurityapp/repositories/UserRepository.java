package com.example.authspringsecurityapp.repositories;

import com.example.authspringsecurityapp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

// Spring Data JPA forcenece
// 1 repositórios como JpaRepository
// 2 queries personalizáveis
// 3 paginação e classificação
// 4 recursos para auditoria de entidades, controle automático de data de criação e atualização
public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login); // passe um login e receba um UserDetails, metodo com apenas 1 linha porque o spring boot le o nome do metodo e infere o sql que precisa fazer
}

