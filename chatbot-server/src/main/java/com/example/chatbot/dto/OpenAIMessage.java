package com.example.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 서버 -> OpenAI
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIMessage {
    private String role;
    private String content;
}
