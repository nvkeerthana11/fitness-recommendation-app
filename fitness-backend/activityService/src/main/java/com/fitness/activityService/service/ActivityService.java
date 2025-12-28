package com.fitness.activityService.service;

import com.fitness.activityService.dto.ActivityRequest;
import com.fitness.activityService.dto.ActivityResponse;
import com.fitness.activityService.mapper.ActivityMapper;
import com.fitness.activityService.model.Activity;
import com.fitness.activityService.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;


    public ActivityResponse trackActivity(ActivityRequest request) {

        boolean isValidUser = Boolean.TRUE.equals(userValidationService.validateUser(request.getUserId()).block());

        if (!isValidUser) {
            throw new RuntimeException("invalid user " + request.getUserId());
        }

        Activity activity = ActivityMapper.INSTANCE.toEntity(request);

        ActivityResponse response = ActivityMapper.INSTANCE.toDto(activityRepository.save(activity));

        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, response);
        } catch (Exception e) {
            log.error("Failed to publish activity to rabbitmq: ", e);
        }

        return response;
    }

    public List<ActivityResponse> getUserActivity(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);
        return ActivityMapper.INSTANCE.toDto(activities);
    }


    public ActivityResponse getActivities(String activityId) {
        Activity activities = activityRepository.findById(activityId).orElseThrow(() -> new RuntimeException("no activity on this id"));
        return ActivityMapper.INSTANCE.toDto(activities);
    }


}
