package com.fitness.gateway.user;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {

    private String id;
    private String keyCloakId;
    private String email;
    private String firstName;
    private String lastName;
    //    private Integer age;
//    private String gender;
//    private Double weightKg;
//    private Double heightCm;
//    private String fitnessGoal;
//    private String experienceLevel;
//    private MedicalConstraint medicalConstraint;
//    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
