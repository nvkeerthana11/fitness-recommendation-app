//package com.fitness.aiservice.service;
//
//import com.fitness.aiservice.model.UserDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//
//@Component
//@RequiredArgsConstructor
//public class UserClient {
//
//    private final WebClient userServiceWebClient;
//
//    public UserDto getUserById(Long userId) {
//        return userServiceWebClient.get()
//                .uri("/api/users/{userId}", userId)
//                .retrieve()
//                .onStatus(
//                        status -> status.value() == 404,
//                        response -> Mono.error(new RuntimeException("User not found: " + userId))
//                )
//                .onStatus(
//                        HttpStatusCode::is4xxClientError,
//                        response -> Mono.error(new RuntimeException("Invalid request for user: " + userId))
//                )
//                .onStatus(
//                        HttpStatusCode::is5xxServerError,
//                        response -> Mono.error(new RuntimeException("User service unavailable"))
//                )
//                .bodyToMono(UserDto.class)
//                .block();
//    }
//}
