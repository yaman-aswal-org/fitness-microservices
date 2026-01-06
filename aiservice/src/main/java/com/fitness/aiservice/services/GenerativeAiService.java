package com.fitness.aiservice.services;


import com.fitness.aiservice.dtos.ChatMessage;
import com.fitness.aiservice.dtos.ChatRequest;
import com.fitness.aiservice.dtos.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class GenerativeAiService {

    private final WebClient webClient;

    @Value("${gen-ai.api.url}")
    private String apiUrl;

    @Value("${gen-ai.api.key}")
    private String apiKey;

    public GenerativeAiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public ChatResponse getAnswer(String question) {

        ChatRequest request = new ChatRequest(
                "meta-llama/Llama-3.1-8B-Instruct",
                List.of(
                        new ChatMessage(
                                "user",
                                question
                        )
                )
        );


        return webClient.post()
                .uri(apiUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();

    }


}
