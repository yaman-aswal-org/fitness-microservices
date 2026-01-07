package com.fitness.aiservice.dtos.ai_response;

import lombok.Data;
import java.util.List;

@Data
public class FitnessAnalysisResponse {

    private AnalysisDto analysis;
    private List<ImprovementDto> improvements;
    private List<SuggestionDto> suggestions;
    private List<String> safety;
}
