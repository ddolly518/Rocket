package com.example.chatbot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Schema(description = "OpenAI 채팅 요청 DTO")
public class OpenAIChatRequest {

    @Schema(description = "모델 이름", example = "gpt-3.5-turbo")
    private final String model;

    @Schema(description = "대화 메시지 목록")
    private final List<OpenAIMessage> messages;

    @Schema(description = "출력 다양성 조절 파라미터 (0~1)", example = "0.7")
    private final double temperature;

    @Schema(description = "JSON 강제 옵션 (REPAIR 전용)", example = "{\"type\":\"json_object\"}")
    private Map<String, String> response_format; // optional

    // 생성자
    public OpenAIChatRequest(String model, List<OpenAIMessage> messages, double temperature) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
    }

    // 생성자 오버로드 (REPAIR 전용)
    public OpenAIChatRequest(String model, List<OpenAIMessage> messages, double temperature, Map<String,String> response_format) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
        this.response_format = response_format;
    }
}
