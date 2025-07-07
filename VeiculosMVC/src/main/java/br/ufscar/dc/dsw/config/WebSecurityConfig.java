package br.ufscar.dc.dsw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // registra nosso provider customizado
            .authenticationProvider(authenticationProvider())

            // autorizações
            .authorizeHttpRequests(authz -> authz
                // libera completamente a API REST
                .requestMatchers("/api/**")
                    .permitAll()
                .requestMatchers("/", "/error", "/login/**", "/index**", "/js/**", "/css/**", "/image/**", "/webjars/**")
                    .permitAll()
                .requestMatchers("/propostas/**")
                    .hasAnyAuthority("CLIENTE", "LOJA")
                .requestMatchers("/veiculos/**")
                    .hasAuthority("LOJA")
                .requestMatchers("/lojas/**", "/clientes/**")
                    .hasAuthority("ADMIN")
                // todo o resto exige autenticação
                .anyRequest()
                    .authenticated()
            )

            // configuração do login
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("email")
                .permitAll()
            )
            // logout
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            )

            // desabilita CSRF apenas para a API REST
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**")
            );

        return http.build();
    }
}
