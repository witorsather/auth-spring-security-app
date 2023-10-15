package com.example.authspringsecurityapp.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails { // minha classe user implementa a interface UserDetails do springsecurity, pois o security espera um UserDetail para maninupar as autenticacoes

    // A melhor prática para gerar identificadores UUID é realmente configurar o Java para gerá-los e depois enviá-los para o banco de dados. Isso é útil porque garante que o identificador seja gerado de maneira
    // única no aplicativo antes de ser persistido no banco de dados. Além disso, permite que você tenha mais controle sobre a criação de identificadores em sua lógica de aplicativo.
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String login;
    private String password;

    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(RegisterDTO registerDTO) {
        this.login = registerDTO.login();
        this.password = registerDTO.password();
        this.role = registerDTO.role();
    }

    // eu defino aqui o que cada enum é, se analyst também deve ter acesso as coisas de usuário ou só de analyst
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else if (this.role == UserRole.ANALYST) {
            return List.of(new SimpleGrantedAuthority("ROLE_ANALYST"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
