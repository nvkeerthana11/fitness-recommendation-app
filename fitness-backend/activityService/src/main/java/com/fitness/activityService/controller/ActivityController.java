package com.fitness.activityService.controller;

import com.fitness.activityService.dto.ActivityRequest;
import com.fitness.activityService.dto.ActivityResponse;
import com.fitness.activityService.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {

    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest activityRequest, @RequestHeader("X-User-ID") String userId) {

        if (userId != null) {
            activityRequest.setUserId(userId);
        }
        return ResponseEntity.ok(activityService.trackActivity(activityRequest));

    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivity(@RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(activityService.getUserActivity(userId));

    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getActivities(@PathVariable String activityId) {
        return ResponseEntity.ok(activityService.getActivities(activityId));

    }


}
