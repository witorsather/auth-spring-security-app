package com.example.authspringsecurityapp.controllers;

import com.example.authspringsecurityapp.domain.user.AuthenticationDTO;
import com.example.authspringsecurityapp.domain.user.LoginResponseDTO;
import com.example.authspringsecurityapp.domain.user.RegisterDTO;
import com.example.authspringsecurityapp.domain.user.User;
import com.example.authspringsecurityapp.infra.security.TokenService;
import com.example.authspringsecurityapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService; // tem 2 métodos, um de gerar token e outro de validar, se eu estou na tela de login, quero gerar o token, então preciso passar um User pro TokenService pra receber um token e retornar pro cliente usar nas próximas vezes

    @Autowired
    private UserService userService;
    // passe um login e uma senha e receba um token
    // pra vc usar nas próximas solicitações sem precisar vir na tela de login já que não é stateful e sim stateless
    @PostMapping("/login") // http://seu-servidor/auth/login
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO) { // AuthenticationDTO só tem String login, String password
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password()); // estou pegando os dados que o usuário mandou e preprando um objeto UsernamePasswordAuthentication pra usar o método abaixo
        // quando você chama authenticationManager.authenticate(usernamePassword),
        // 1 o Spring Security utiliza o UserDetailsService para carregar os detalhes do usuário com base no login e,
        // 2  em seguida, verifica se a senha fornecida no UsernamePasswordAuthenticationToken corresponde à senha armazenada nos detalhes do usuário carregados pelo UserDetailsService.
        // 3 Se a autenticação for bem-sucedida, o Spring Security criará um token de autenticação para o usuário, que pode ser usado para autorizar as próximas solicitações.
        var auth = this.authenticationManager.authenticate(usernamePassword); // esse método authenticate me retorna um objeto Authentication Autenticação que dentro dele tem um usuário

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token)); // dto LoginResponseDTO só tem um String token
    }

    //passe um login, password e role e eu salvo esse usuário no banco
    @PostMapping("/register")  // Este é o endpoint para criar um novo usuário
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        User newUser = userService.registerUser(data); // Chama o serviço para registrar o novo usuário com os dados fornecidos
        
        URI location = ServletUriComponentsBuilder  // Cria uma URI (Uniform Resource Identifier) para o novo usuário recém-criado
                .fromCurrentRequest()  // Pega a URI atual do endpoint
                .path("/{id}")         // Adiciona "/{id}" ao caminho da URI
                .buildAndExpand(newUser.getId())  // Substitui "{id}" pelo ID real do novo usuário
                .toUri();              // Converte em uma URI completa
        
        return ResponseEntity.created(location).build(); // Retorna uma resposta HTTP 201 Created com a URI do novo usuário no cabeçalho "Location"
    }
}
