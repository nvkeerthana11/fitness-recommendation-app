package com.fitness.aiservice.model;


import com.fitness.aiservice.enums.MedicalConstraint;
import com.fitness.aiservice.enums.UserRole;

import java.time.LocalDateTime;


public record UserDto(

        String id,
        String email,
        String firstName,
        String lastName,
        Integer age,
        String gender,
        Double weightKg,
        Double heightCm,
        String fitnessGoal,
        String experienceLevel,
        MedicalConstraint medicalConstraint,
        UserRole role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

