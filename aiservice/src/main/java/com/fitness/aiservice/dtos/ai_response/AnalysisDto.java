package com.fitness.aiservice.dtos.ai_response;

import lombok.Data;

@Data
public class AnalysisDto {
    private String overall;
    private String pace;
    private String heartRate;
    private String caloriesBurned;
}
