package com.fitness.activityService.dto;

import com.fitness.activityService.enums.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityRequest {

    private String userId;          // set by backend
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private Map<String, Object> additionalMetrics;
    private LocalDateTime startTime;
}
