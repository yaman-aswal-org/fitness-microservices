package com.fitness.activityservice.services;

import com.fitness.activityservice.dtos.ActivityRequest;
import com.fitness.activityservice.dtos.ActivityResponse;
import com.fitness.activityservice.models.Activity;
import com.fitness.activityservice.producers.ActivityProducer;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final ActivityProducer activityProducer;

    public ActivityResponse trackActivity(ActivityRequest request) {

        boolean isValidUser = userValidationService.validateUser(request.getUserId());

        if (!isValidUser) {
            throw new RuntimeException("Invalid User: " + request.getUserId());
        }

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .startTime(request.getStartTime())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        activityProducer.sendActivity(savedActivity);

        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity) {
        ActivityResponse activityResponse = new ActivityResponse();
        activityResponse.setId(activity.getId());
        activityResponse.setUserId(activity.getUserId());
        activityResponse.setType(activity.getType());
        activityResponse.setStartTime(activity.getStartTime());
        activityResponse.setDuration(activity.getDuration());
        activityResponse.setCaloriesBurned(activity.getCaloriesBurned());
        activityResponse.setAdditionalMetrics(activity.getAdditionalMetrics());
        activityResponse.setCreatedAt(activity.getCreatedAt());
        activityResponse.setUpdatedAt(activity.getUpdatedAt());
        return activityResponse;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);

        return activities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String activityId) {

        return activityRepository
                .findById(activityId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + activityId));
    }
}
