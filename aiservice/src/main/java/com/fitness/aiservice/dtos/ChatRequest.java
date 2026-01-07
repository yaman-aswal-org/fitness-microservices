package com.fitness.aiservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatRequest {
    String model;
    List<ChatMessage> messages;
}
