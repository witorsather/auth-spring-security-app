package com.example.authspringsecurityapp.services;

import com.example.authspringsecurityapp.domain.user.RegisterDTO;
import com.example.authspringsecurityapp.domain.user.User;
import com.example.authspringsecurityapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // passe um login, uma password e uma role, crio um User no banco e te retorno ele
    public User registerUser(RegisterDTO data) { // RegisterDTO tem login, password e role
        User newUser = userRepository.save(new User(data));
        return newUser;
    }
}
