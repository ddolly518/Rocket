package com.example.chatbot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "OpenAI 채팅 요청 DTO")
public class OpenAIChatRequest {

    @Schema(description = "모델 이름", example = "gpt-3.5-turbo")
    private String model;

    @Schema(description = "대화 메시지 목록")
    private List<OpenAIMessage> messages;

    @Schema(description = "출력 다양성 조절 파라미터 (0~1)", example = "0.7")
    private double temperature;
}
