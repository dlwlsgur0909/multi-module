package com.example.config;

import com.example.jwt.JwtFilter;
import com.example.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
//                .cors((cors) -> cors.configurationSource(request -> {
//                    CorsConfiguration configuration = new CorsConfiguration();
//                    configuration.setAllowedOrigins(
//                            List.of("http://localhost:3000")
//                    );
//                    configuration.setAllowedMethods(Collections.singletonList("*"));
//                    configuration.setAllowedHeaders(Collections.singletonList("*"));
//                    configuration.setExposedHeaders(Collections.singletonList("Content-Disposition"));
//                    configuration.setAllowCredentials(true);
//
//                    return configuration;
//                }))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling(handler -> handler.authenticationEntryPoint(customAuthenticationEntryPoint))
//                .exceptionHandling(handler -> handler.accessDeniedHandler(customAccessDeniedHandler))
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers("/api/auth/**").permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
