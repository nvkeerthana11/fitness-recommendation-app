package com.fitness.gateway.user;

import com.fitness.gateway.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final WebClient userServiceWebClient;

    public Mono<Boolean> validateUser(String userId) {

        // Defensive check (VERY important)
        if (userId == null || userId.isBlank()) {
            log.warn("validateUser called with null or empty userId");
            return Mono.just(false);
        }

        return userServiceWebClient.get()
                .uri("/api/users/{userId}/validate", userId)
                .retrieve()
                .onStatus(
                        status -> status == HttpStatus.NOT_FOUND,
                        response -> Mono.empty()   // treat as "user does not exist"
                )
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> Mono.error(
                                new RuntimeException("Invalid request for userId: " + userId))
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        response -> Mono.error(
                                new RuntimeException("User-service failure while validating userId: " + userId))
                )
                .bodyToMono(Boolean.class)
                .onErrorReturn(false);
    }

    public Mono<UserResponse> registerUser(RegisterRequest registerRequest) {


        return userServiceWebClient.post().uri("/api/users/register")
                .bodyValue(registerRequest)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                        return Mono.error(new RuntimeException("Bad Request: " + e.getMessage()));
                    else if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
                        return Mono.error(new RuntimeException(("Internal server error: " + e.getMessage())));
                    return Mono.error(new RuntimeException("Unexpected error: " + e.getMessage()));

                });
    }
}
