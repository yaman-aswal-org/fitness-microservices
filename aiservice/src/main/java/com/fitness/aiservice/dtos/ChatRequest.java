package com.fitness.aiservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ChatRequest {
    String model;
    List<ChatMessage> messages;
}
