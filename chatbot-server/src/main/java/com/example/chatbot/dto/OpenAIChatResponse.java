package com.example.chatbot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OpenAIChatResponse {

    // GPT가 생성한 응답 선택지 리스트
    private List<Choice> choices;

    @Getter
    @NoArgsConstructor
    public static class Choice {
        private OpenAIMessage message;
    }
}
