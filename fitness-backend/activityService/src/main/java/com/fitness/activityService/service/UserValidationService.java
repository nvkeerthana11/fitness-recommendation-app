package com.fitness.activityService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final WebClient userServiceWebClient;

    public Mono<Boolean> validateUser(String userId) {
        return userServiceWebClient.get()
                .uri("/api/users/{userId}/validate", userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new RuntimeException("User not found: " + userId));
                    }
                    return Mono.error(new RuntimeException("Invalid Request for: " + userId));
                })
                .bodyToMono(Boolean.class)
                .onErrorReturn(false);
    }
}
