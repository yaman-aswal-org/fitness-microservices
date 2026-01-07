package com.fitness.aiservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.dtos.ActivityQueueResponse;
import com.fitness.aiservice.dtos.ChatResponse;
import com.fitness.aiservice.dtos.ai_response.FitnessAnalysisResponse;
import com.fitness.aiservice.dtos.ai_response.ImprovementDto;
import com.fitness.aiservice.dtos.ai_response.SuggestionDto;
import com.fitness.aiservice.models.Recommendation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {

    private final GenerativeAiService generativeAiService;

    public Recommendation generateRecommendation(ActivityQueueResponse activityQueueResponse) {
        String prompt = createPromptForActivity(activityQueueResponse);
        ChatResponse aiResponse = generativeAiService.getAnswer(prompt);
        String content = aiResponse.choices.get(0).message.content;
        return processAiResponse(activityQueueResponse, content);
    }

    private Recommendation processAiResponse(ActivityQueueResponse activity, String content) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FitnessAnalysisResponse response = objectMapper.readValue(content, FitnessAnalysisResponse.class);

            StringBuilder fullAnalysis = addAnalysisSection(response);

            List<String> improvements = getImprovements(response);

            List<String> suggestionStrings = getSuggestions(response);

            return Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .activityType(activity.getType())
                    .recommendation(fullAnalysis.toString())
                    .improvements(improvements)
                    .suggestions(suggestionStrings)
                    .safety(response.getSafety())
                    .createdAt(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return createDefaultRecommendation(activity);
        }
    }

    private @NonNull List<String> getSuggestions(FitnessAnalysisResponse response) {
        List<String> suggestionStrings = new ArrayList<>();

        if (response.getSuggestions() != null) {
            for (SuggestionDto suggestion : response.getSuggestions()) {

                String workout = suggestion.getWorkout() != null ? suggestion.getWorkout() : "";
                String description = suggestion.getDescription() != null ? suggestion.getDescription() : "";

                suggestionStrings.add(
                        "Workout: " + workout + "\nDescription: " + description
                );
            }
        }
        return suggestionStrings;
    }

    private @NonNull List<String> getImprovements(FitnessAnalysisResponse response) {
        List<String> improvements = new ArrayList<>();

        if (response.getImprovements() != null) {
            for (ImprovementDto improvement : response.getImprovements()) {

                String area = improvement.getArea() != null ? improvement.getArea() : "";
                String rec = improvement.getRecommendation() != null ? improvement.getRecommendation() : "";

                improvements.add(
                        "Area: " + area + "\nRecommendation: " + rec
                );
            }
        }

        return improvements;
    }

    private @NonNull StringBuilder addAnalysisSection(FitnessAnalysisResponse response) {
        StringBuilder fullAnalysis = new StringBuilder();

        fullAnalysis.append("Overall:");
        if (response.getAnalysis().getOverall() != null) {
            fullAnalysis.append(response.getAnalysis().getOverall());
        }

        fullAnalysis.append("\nPace:");
        if (response.getAnalysis().getPace() != null) {
            fullAnalysis.append(response.getAnalysis().getPace());
        }

        fullAnalysis.append("\nHeart Rate:");
        if (response.getAnalysis().getHeartRate() != null) {
            fullAnalysis.append(response.getAnalysis().getHeartRate());
        }

        fullAnalysis.append("\nCalories:");
        if (response.getAnalysis().getCaloriesBurned() != null) {
            fullAnalysis.append(response.getAnalysis().getCaloriesBurned());
        }
        return fullAnalysis;
    }

    private Recommendation createDefaultRecommendation(ActivityQueueResponse activity) {
        return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .activityType(activity.getType())
                .recommendation("Unable to generate detailed analysis")
                .improvements(Collections.singletonList("Continue with your current routine"))
                .suggestions(Collections.singletonList("Consider consulting a fitness professional"))
                .safety(Arrays.asList(
                        "Always warm up before exercise",
                        "Stay hydrated",
                        "Listen to your body"
                ))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private String createPromptForActivity(ActivityQueueResponse activity) {
        return String.format("""
                        Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
                        {
                          "analysis": {
                            "overall": "Overall analysis here",
                            "pace": "Pace analysis here",
                            "heartRate": "Heart rate analysis here",
                            "caloriesBurned": "Calories analysis here"
                          },
                          "improvements": [
                            {
                              "area": "Area name",
                              "recommendation": "Detailed recommendation"
                            }
                          ],
                          "suggestions": [
                            {
                              "workout": "Workout name",
                              "description": "Detailed workout description"
                            }
                          ],
                          "safety": [
                            "Safety point 1",
                            "Safety point 2"
                          ]
                        }
                        
                        Analyze this activity:
                        Activity Type: %s
                        Duration: %d minutes
                        Calories Burned: %d
                        Additional Metrics: %s
                        
                        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
                        Ensure the response follows the EXACT JSON format shown above.
                        """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );
    }


}
