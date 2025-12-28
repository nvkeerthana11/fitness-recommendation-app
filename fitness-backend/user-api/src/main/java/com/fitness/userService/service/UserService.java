package com.fitness.userService.service;

import com.fitness.userService.dto.RegisterRequest;
import com.fitness.userService.dto.UserResponse;
import com.fitness.userService.mapper.UserMapper;
import com.fitness.userService.model.User;
import com.fitness.userService.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            User existingUser = userRepository.findByEmail(request.email());
            return UserMapper.INSTANCE.toDto(existingUser);
        }
        User user = UserMapper.INSTANCE.toEntity(request);
        return UserMapper.INSTANCE.toDto(userRepository.save(user));
    }

    public UserResponse getUserProfile(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user doesnt exist"));
        return UserMapper.INSTANCE.toDto(user);
    }

    public boolean existsByKeyCloakId(String userId) {

        return userRepository.existsByKeyCloakId(userId);
    }

}
