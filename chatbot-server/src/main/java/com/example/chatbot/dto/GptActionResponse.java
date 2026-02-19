package com.example.chatbot.dto;

public record GptActionResponse (
    String action,
    String product,
    String issue
) {}
