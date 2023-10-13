package com.example.authspringsecurityapp.infra.security;

import com.example.authspringsecurityapp.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // esse component do spring framework é responsával por interceptar todas as requisições http e uma vez com elas na mão posso autenticar, autorizar e trabalhar a segurança
// com base no token recebido eu posso
// 1 - Autenticar o usuário: Verificar se a solicitação contém informações de autenticação válidas, como um token ou credenciais de login, e autenticar o usuário com base nessas informações.
// 2 - Autorizar o acesso: Verificar se o usuário autenticado tem permissão para acessar o recurso solicitado. Isso envolve a verificação das configurações de autorização, como papéis de usuário ou outras políticas de acesso.
// 3 - Realizar outras tarefas de segurança: Você também pode implementar verificações de segurança adicionais, como proteção contra ataques de segurança, manipulação de cabeçalhos de solicitação, entre outros.
public class SecurityFilter extends OncePerRequestFilter {  // OncePerRequestFilter garante que ele será executado apenas 1 vez por solicitação
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    // filterChain - É um objeto que representa a cadeia de filtros do Spring Security. Ele é usado para permitir que a solicitação continue a ser processada pelos próximos filtros na cadeia após este filtro.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Recupera o token da solicitação (se existir)
        var token = this.recoverToken(request);

        // Verifica se um token foi encontrado
        if (token != null) {
            // Valida o token e obtém o login do usuário associado a ele
            var login = tokenService.validateToken(token);

            // Recupera os detalhes do usuário com base no login
            UserDetails user = userRepository.findByLogin(login);

            // Cria uma instância de autenticação usando os detalhes do usuário e nenhuma senha (já que é um token)
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            // Define a autenticação atual para o usuário
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Após essa linha, o usuário está autenticado e pode acessar recursos protegidos. No entanto, a autorização real para acessar recursos será verificada no próximo filtro,
        }

        // Permite que a solicitação continue a ser processada pelos filtros seguintes na cadeia de filtros, o próximo inclusive é para falar sobre autorização
        filterChain.doFilter(request, response);
    }
    
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
