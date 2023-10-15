package com.example.authspringsecurityapp.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Quando você usa essas anotações @Configuration e @EnableWebSecurity em uma classe de configuração, você está indicando que deseja personalizar as configurações de segurança da sua aplicação web usando o Spring Security.
// É o ponto de partida para configurar como sua aplicação lida com autenticação e autorização, protegendo recursos e controlando o acesso dos usuários.

// @Configuration é uma anotação do spring framework, esta anotação indica que a classe é uma configuração do Spring, o que significa que ela contém informações de configuração para a aplicação.
// @EnableWebSecurity é uma anotação que ativa a configuração de segurança da web fornecida pelo Spring Security. Ela permite que você defina regras de segurança para sua aplicação, como autenticação de usuários, autorização de acesso a recursos protegidos e outras configurações relacionadas à segurança na web.

// Objetivo final: aplicar uma configuração de segurança para cada requisição http enviado ao servidor de autenticação
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter; // injeto uma instância de SecurityFilter pra uso posterior, isso é injeção de dependência, o objetivo é pegar a requisição http passar pra ele filtrar e depois continuar com os meus filtros dessa classe

    @Bean
    // Este método cria e configura um SecurityFilterChain que intercepta todas as requisições HTTP e aplica uma configuração de segurança, determinando quais endpoints podem ser acessados com base em regras específicas, como autenticação e autorização. 
    // como é um bean e é o próprio spring framework que gerencia seu estado, esse metodo ira interceptar todas as requisições http e pra cada uma delas vai chamá-la de httpSecurity e assim poder aplicar uma configuração de segurança
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity // o httpSecurity já possui as configurações padrão relacionadas ao CSRF (Cross-Site Request Forgery), à gerência de sessão (session management e authorize.
                .csrf(csrf -> csrf.disable()) // você está efetivamente substituindo as configurações padrão por aquelas que deseja aplicar.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() // o permitAll inclui permitir todo mundo sem autenticação e autorização
                        .requestMatchers(HttpMethod.POST, "/auth/register").hasRole("ADMIN") // o hasRole quer dizer que a pessoa já tem que está autenticada pra daí olhar sua autorização ADMIN, ou seja preciso passar bearer token
                        .requestMatchers(HttpMethod.POST, "/product").hasRole("ANALYST") // As linhas anteriores especificam que o usuário deve estar autenticado e ter uma determinada função (role) para acessar endpoints específicos.
                        .anyRequest().authenticated() // Já a última linha indica que para todos os outros endpoints não mencionados anteriormente, o usuário precisa apenas estar autenticado, independentemente de sua função (role).
                )
                // securityFilter => você deseja que o seu filtro de segurança seja executado antes da autenticação básica. O securityFilter pode realizar a validação do token JWT e outras verificações de segurança antes da autenticação padrão.
                // UsernamePasswordAuthenticationFilte => (username, password) é um filtro padrão do Spring Security que lida com a autenticação por nome de usuário e senha.
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build(); // Essa chamada conclui a configuração do seu SecurityFilterChain e a constrói. Basicamente, após todas as configurações e personalizações definidas anteriormente, você chama .build() para criar a instância final do SecurityFilterChain, que será usada para processar as solicitações HTTP e aplicar as regras de segurança configuradas.
    }

    // passe username e password que eu autentico se vc é um usuário válido no banco
    // ele só verifica se o usuário existe no banco e se a senha bate
    
    // O método authenticationManager.authenticate(usernamePassword) é responsável por iniciar o processo de autenticação no Spring Security. Durante esse processo,
    // o Spring Security utiliza o UserDetailsService para carregar os detalhes do usuário com base no nome de usuário (login) fornecido no usernamePassword.
    // Isso significa que, ao chamar authenticationManager.authenticate(usernamePassword), o Spring Security automaticamente recorre ao UserDetailsService para obter os detalhes do usuário correspondente ao login fornecido.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // PasswordEncoder é uma interface do spring security para codificar e decodificar senhas
        return new BCryptPasswordEncoder(); // BCryptPassworEncoder é uma classe que implementa a interface PasswordEncoder do Spring Security e usa o algoritmo de criptografia BCrypt
        // algoritm BCrypt é uma função de hash segura, transforma a senha em uma representação hash, ele gera uma salga, tipo uma secret mas com valor aleatório impedindo atacantes com ataque de força bruta de usar tabelas hash pré-computadas
    }
}
