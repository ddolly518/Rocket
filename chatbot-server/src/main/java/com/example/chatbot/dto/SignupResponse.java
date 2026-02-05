package com.example.chatbot.dto;

public record SignupResponse (
        String email,
        String nickname,
        String message
) {}
