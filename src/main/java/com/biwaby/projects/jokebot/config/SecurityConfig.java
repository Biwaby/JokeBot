package com.biwaby.projects.jokebot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth ->
                        auth
                                // права всех
                                .requestMatchers(HttpMethod.GET, "/jokes/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                                // только для зарегистрированных (имеющих роль USER, MODERATOR или ADMIN)
                                .requestMatchers(HttpMethod.POST, "/jokes").authenticated()
                                // только для имеющих роль MODERATOR и ADMIN
                                .requestMatchers(HttpMethod.PUT, "/jokes/{id}").hasAnyAuthority("MODERATOR", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/jokes/{id}").hasAnyAuthority("MODERATOR", "ADMIN")
                                // только для имеющих роль ADMIN
                                .requestMatchers(HttpMethod.GET, "/actuator/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/users/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ADMIN")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
