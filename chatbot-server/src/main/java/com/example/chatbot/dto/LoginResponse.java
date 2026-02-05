package com.example.chatbot.dto;

public record LoginResponse  (
        String email,
        String nickname,
        String message
) {}
