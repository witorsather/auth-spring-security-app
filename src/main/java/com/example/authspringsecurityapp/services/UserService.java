package com.example.authspringsecurityapp.services;

import com.example.authspringsecurityapp.domain.user.RegisterDTO;
import com.example.authspringsecurityapp.domain.user.User;
import com.example.authspringsecurityapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // passe um login, uma password e uma role, crio um User no banco e te retorno ele
    public User registerUser(RegisterDTO data) { // RegisterDTO tem login, password e role
        String encryptedPassword = passwordEncoder.encode(data.password());

        User newUser = new User(data.login(), encryptedPassword, data.role());
        
        return userRepository.save(newUser);
    }
}
