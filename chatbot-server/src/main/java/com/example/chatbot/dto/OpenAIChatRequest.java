package com.example.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIChatRequest {
    private String model;
    private List<OpenAIMessage> messages;
    private double temperature;
}
