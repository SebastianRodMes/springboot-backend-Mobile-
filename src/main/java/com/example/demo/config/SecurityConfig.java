package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitar CSRF (no se necesita para APIs REST)
            .csrf(csrf -> csrf.disable())
            
            // Configurar qué endpoints son públicos y cuáles requieren autenticación
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()     // Permitir /auth/signup y /auth/login
                .requestMatchers("/user/**").permitAll()     // Permitir /user/** (temporal para testing)
                .requestMatchers("/tickets/**").permitAll()  // Permitir /tickets/** (temporal para testing)
                .anyRequest().authenticated()                // Todo lo demás requiere autenticación
            )
            
            // Deshabilitar sesiones (usamos JWT, no sesiones)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
        
        return http.build();
    }
}