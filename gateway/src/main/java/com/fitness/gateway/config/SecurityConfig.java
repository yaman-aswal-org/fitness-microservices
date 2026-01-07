package com.fitness.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {

        http.csrf(csrfSpec -> csrfSpec.disable());

        http.authorizeExchange(authorizeExchangeSpec ->
                authorizeExchangeSpec
                        .pathMatchers("/actuator/*").permitAll()
                        .anyExchange().authenticated());

        http.oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                .jwt(Customizer.withDefaults()));

        return http.build();
    }

}
