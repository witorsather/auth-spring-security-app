package com.example.authspringsecurityapp.services;

import com.example.authspringsecurityapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

 // O Spring Security utiliza a implementação do UserDetailsService para carregar os detalhes do usuário durante o processo de autenticação. Quando um usuário tenta fazer login,
 // o Spring Security chama o método loadUserByUsername com o nome de usuário fornecido e, em seguida, verifica as informações de autenticação, como a senha, que estão no objeto UserDetails retornado.
// A classe AuthorizationService que implementa a interface UserDetailsService é gerenciada pelo Spring Security e não precisa ser chamada explicitamente em nenhum lugar do meu  código.
// O Spring Security cuida da integração com esta classe automaticamente.
// toda vez que você precisar autenticar um usuário na sua aplicação, o Spring Security cuidará automaticamente de chamar o loadUserByUsername da classe AuthorizationService para carregar os detalhes do usuário.
// Você não precisa chamar esse método manualmente. Além disso, sempre que você interagir com uma entidade User que implementa a interface UserDetails, o Spring Security utilizará os detalhes dessa entidade para fins de autenticação e autorização.
@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;


    // passo um username(login) e recebo um UserDetails(que é onde está as authorities do usuário, o que ele está autorizado a mexer)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // toda vez que precisar autenticar um usuário(ver se existe no banco) o spring security chama de forma automática esse método
        return userRepository.findByLogin(username);
    }
}
