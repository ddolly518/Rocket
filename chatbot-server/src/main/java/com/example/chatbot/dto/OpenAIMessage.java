package com.example.chatbot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 서버 -> OpenAI
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "OpenAI 메시지 DTO")
public class OpenAIMessage {

    @Schema(description = "보낸 이", example = "user", allowableValues = {"user", "assistant"})
    private String role;

    @Schema(description = "메시지 내용", example = "안녕하세요!")
    private String content;
}
