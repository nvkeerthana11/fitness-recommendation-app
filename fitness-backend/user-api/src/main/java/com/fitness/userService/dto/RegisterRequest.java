package com.fitness.userService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(

        @NotBlank
        @Email
        String email,

        String keyCloakId,

        @NotBlank
        String password,

        String firstName,

        String lastName

) {
}
