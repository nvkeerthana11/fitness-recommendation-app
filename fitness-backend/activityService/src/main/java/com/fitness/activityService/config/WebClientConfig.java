package com.fitness.activityService.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /**
     * In this configuration, you are setting up WebClient, which is Spring’s modern, non-blocking, and
     * reactive way to perform HTTP requests. Because you are using it within a
     * microservices environment, you've added Client-side Load Balancing.//
     * 1. @LoadBalanced
     * Without it: WebClient would look for a physical server at the URL http://USER-SERVICE.
     * Since USER-SERVICE isn't a real domain name (like google.com), the request would fail.
     * With it: Spring Cloud intercepts the request. It sees USER-SERVICE,
     * goes to the Eureka Server, finds the actual IP addresses of the user-service instances,
     * and picks one to send the request to.
     **/
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    //we have this userServiceWebClient is a bean configured to point to userservice url
    @Bean
    public WebClient userServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl("http://USER-SERVICE")
                .build();
    }

    @Bean
    public WebClient activityServiceWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl("http://ACTIVITY-SERVICE")
                .build();
    }

}
