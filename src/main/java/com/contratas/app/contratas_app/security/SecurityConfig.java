package com.contratas.app.contratas_app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler successHandler;

    public SecurityConfig(OAuth2LoginSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // ❌ CSRF off (ok para APIs / HTML simple)
            .csrf(csrf -> csrf.disable())

            // 🔓 RUTAS PÚBLICAS
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",                 // index.html
                    "/login",
                    "/oauth2/**",        // OAuth Google
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()
                .anyRequest().authenticated()
            )

            // 🔐 LOGIN CON GOOGLE
            .oauth2Login(oauth -> oauth
                .successHandler(successHandler) // ← GUARDA EN BD Y REDIRIGE
            )

            // 🚪 LOGOUT
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
            );

        return http.build();
    }
}
