package com.example.chatbot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "OpenAI 채팅 응답 DTO")
public class OpenAIChatResponse {

    @Schema(description = "OpenAI가 생성한 메시지 목록")
    private List<Choice> choices;

    @Getter
    @NoArgsConstructor
    public static class Choice {
        private OpenAIMessage message;
    }
}
