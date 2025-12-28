package com.fitness.userService.model;

import com.fitness.userService.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String keyCloakId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;

//    @Column(nullable = false)
//    private Integer age;
//
//    private String gender;
//
//    @Column(nullable = false)
//    private Double weightKg;
//
//    @Column(nullable = false)
//    private Double heightCm;
//
//    private String fitnessGoal;
//    private String experienceLevel;
//
//    @Enumerated(EnumType.STRING)
//    private MedicalConstraint medicalConstraint = MedicalConstraint.NONE;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
