package com.example.authspringsecurityapp.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.authspringsecurityapp.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {
    @Value("${api.security.token.secret}") // Referência à propriedade definida no application.properties
    private String secret; // segredo da aplicacação que é adicionado na criação do token jwt

    // passo um usuário (User Details spring security) e me retorna um token criado na hora, recém saído do forno para aquele
    // usuário específico, no token tem as informações de emissor, destinátario, data de expiração e uma assinatura diginal(selo)
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret); // Passo 1: Escolha um algoritmo de assinatura, neste caso, usamos HMAC256 com a secret.

            String token = JWT.create() // Passo 2: Crie um token JWT com as informações necessárias.
                    .withIssuer("auth-api") // Define o emissor (issuer) do token. Indica que foi emitido por uma fonte confiável.
                    .withSubject(user.getLogin())  // Define o assunto (subject) do token como o login do usuário.
                    .withExpiresAt(genExpirationDate()) // Define a data de expiração do token. O token será válido até essa data e hora.
                    .sign(algorithm); // Assina digitalmente o token com o algoritmo HMAC256 secret que criamos. Isso garante a autenticidade do token. Sem a secret correta, o token é inválido e para descriptografar ele precisa da secret.

            return token; // Retorna o token JWT gerado.
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token", exception); // Em caso de erro na criação do token, lançamos uma exceção.
        }
    }

    // passe um token e receba o login que estava nesse token
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret); // algoritmo usado para descriptografar o token com a nossa secret
            return JWT.require(algorithm) // require método do JWT que passando o nosso algoritmo com a secret ele consegue descriptografar o token
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)  // decodifica o token
                    .getSubject(); // pega o Subject que é o login que tava no token e retorna pra quem chamou o método
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
