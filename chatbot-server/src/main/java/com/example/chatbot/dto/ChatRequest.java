package com.example.chatbot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 사용자 -> 서버
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {

    private Long conversationId;

    @NotBlank(message = "message는 필수입니다.")
    private String message;
}