package com.fitness.userService.dto;

import java.time.LocalDateTime;

public record UserResponse(

        String id,
        String keyCloakId,
        String email,
        String firstName,
        String lastName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
