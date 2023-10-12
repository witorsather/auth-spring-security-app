package com.example.authspringsecurityapp.repositories;

import com.example.authspringsecurityapp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login); // passe um login e receba um UserDetails, metodo com apenas 1 linha porque o spring boot le o nome do metodo e infere o sql que precisa fazer
}
