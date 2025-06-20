package br.ufscar.dc.dsw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import br.ufscar.dc.dsw.security.UsuarioDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UsuarioDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // registra nosso provider customizado
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(authz -> authz
                // recursos públicos
                .requestMatchers("/", "/error", "/login/**", "/index**", "/js/**", "/css/**", "/image/**", "/webjars/**")
                    .permitAll()
                // apenas clientes e admins podem criar/ver propostas
                .requestMatchers("/propostas/**")
                    .hasAnyAuthority("CLIENTE")
                // apenas lojas admins podem cadastrar/editar veículos
                .requestMatchers("/veiculos/**")
                    .hasAnyAuthority("LOJA")
                // apenas administrador pode gerenciar clientes e lojas
                .requestMatchers("/lojas/**", "/clientes/**")
                    .hasAuthority("ADMIN")
                // todas as outras requisições exigem autenticação
                .anyRequest()
                    .authenticated()
            )
            .formLogin(form -> form
                // nossa página de login
                .loginPage("/login")
                // parâmetro de usuário passa a ser "email" em vez de "username"
                .usernameParameter("email")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );
        return http.build();
    }
}
