package br.ufscar.dc.dsw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.ufscar.dc.dsw.security.UsuarioDetailsServiceImpl;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public UsuarioDetailsServiceImpl userDetailsService() {
        return new UsuarioDetailsServiceImpl();
    }

    @Bean
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // registra nosso DaoAuthenticationProvider
            .authenticationProvider(authenticationProvider())

            // regras de autorização
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/error", "/login", "/css/**", "/js/**", "/webjars/**").permitAll()
                .requestMatchers("/clientes/**", "/lojas/**", "/editoras/**", "/livros/**")
                    .hasRole("ADMIN")
                .requestMatchers("/veiculos/cadastrar", "/veiculos/listar-por-loja", "/propostas/analise")
                    .hasRole("LOJA")
                .requestMatchers("/propostas/cadastrar", "/propostas/listar-por-cliente")
                    .hasRole("CLIENTE")
                .anyRequest().permitAll()
            )

            // configuração do form-login
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("email")      // usa “email” como campo de usuário
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )

            // configuração do logout
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )

            // página de acesso negado
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/403")
            )

            .csrf(withDefaults())
        ;
        return http.build();
    }
}
