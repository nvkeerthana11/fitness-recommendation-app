package com.fitness.gateway;

import com.fitness.gateway.user.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeyCloakUserSyncFilter implements WebFilter {

    private final UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String userIdHeader = exchange.getRequest().getHeaders().getFirst("X-User-ID");
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        log.info("Incoming request: {} {}",
                exchange.getRequest().getMethod(),
                exchange.getRequest().getURI());

        log.info("X-User-ID header: {}", userIdHeader);
        log.info("Authorization header present: {}", token != null);

        RegisterRequest request = getUserDetails(token);

        if (request != null) {
            log.info("Parsed JWT details: keycloakId={}, email={}",
                    request.getKeyCloakId(), request.getEmail());
        } else {
            log.warn("JWT parsing returned null RegisterRequest");
        }

        if (userIdHeader == null && request != null) {
            userIdHeader = request.getKeyCloakId();
            log.info("X-User-ID header missing. Using keycloakId from JWT: {}", userIdHeader);
        }

        if (userIdHeader != null || token != null) {

            String finalUserIdHeader = userIdHeader;
            log.info("Proceeding with user sync. finalUserIdHeader={}", finalUserIdHeader);

            String finalUserIdHeader1 = userIdHeader;
            return userService.validateUser(userIdHeader)
                    .doOnSubscribe(sub ->
                            log.info("➡️ Calling validateUser(userId={})", finalUserIdHeader1)
                    )
                    .doOnNext(exists ->
                            log.info("validateUser result: exists={}", exists)
                    )
                    .flatMap(exists -> {
                        if (!exists) {
                            log.info("User does NOT exist. Attempting registration.");

                            RegisterRequest registerRequest = getUserDetails(token);
                            if (registerRequest != null) {
                                log.info("Registering user with email={} keycloakId={}",
                                        registerRequest.getEmail(),
                                        registerRequest.getKeyCloakId());

                                return userService.registerUser(registerRequest)
                                        .doOnSuccess(res ->
                                                log.info("User registration completed successfully"))
                                        .doOnError(e ->
                                                log.error("❌ User registration failed", e))
                                        .then(Mono.empty());
                            } else {
                                log.warn("RegisterRequest is null. Skipping registration.");
                                return Mono.empty();
                            }
                        } else {
                            log.info("User already exists. Skipping sync.");
                            return Mono.empty();
                        }
                    })
                    .then(Mono.defer(() -> {
                        log.info("Mutating request with X-User-ID={}", finalUserIdHeader);

                        ServerHttpRequest mutatedRequest = exchange.getRequest()
                                .mutate()
                                .header("X-User-ID", finalUserIdHeader)
                                .build();

                        return chain.filter(
                                exchange.mutate().request(mutatedRequest).build());
                    }));
        }

        log.info("Skipping KeyCloakUserSyncFilter. No userId or token found.");
        return chain.filter(exchange);
    }

    private RegisterRequest getUserDetails(String token) {
        try {
            log.info("Attempting to parse JWT");

            String tokenWithoutBearer = token.replace("Bearer", "").trim();
            SignedJWT signedJWT = SignedJWT.parse(tokenWithoutBearer);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            RegisterRequest request = new RegisterRequest();
            request.setEmail(claims.getStringClaim("email"));
            request.setKeyCloakId(claims.getSubject());
            request.setFirstName(claims.getStringClaim("firstName"));
            request.setLastName(claims.getStringClaim("lastName"));
            request.setPassword("2000");
            log.info("JWT parsed successfully: subject={}, email={}",
                    request.getKeyCloakId(), request.getEmail());

            return request;

        } catch (Exception e) {
            log.error("❌ Failed to parse JWT", e);
            return null;
        }
    }
}
