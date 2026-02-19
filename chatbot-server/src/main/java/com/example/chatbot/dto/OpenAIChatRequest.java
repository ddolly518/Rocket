package com.example.chatbot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "OpenAI 채팅 요청 DTO")
public record OpenAIChatRequest (

    @Schema(description = "모델 이름", example = "gpt-3.5-turbo")
    String model,

    @Schema(description = "대화 메시지 목록")
    List<OpenAIMessage> messages,

    @Schema(description = "출력 다양성 조절 파라미터 (0~1)", example = "0.7")
    double temperature
) {}
