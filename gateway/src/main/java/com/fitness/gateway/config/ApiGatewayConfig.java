package com.fitness.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        var router = builder.routes();

        router.route(
                "user-service", r -> r
                        .path("/api/users/**")
                        .uri("lb://USER-SERVICE")
        );

        router.route(
                "activity-service", r -> r
                        .path("/api/activities/**")
                        .uri("lb://ACTIVITY-SERVICE")
        );

        router.route(
                "ai-service", r -> r
                        .path("/api/recommendations/**")
                        .uri("lb://AI-SERVICE")
        );

        return router
                .build();
    }
}
