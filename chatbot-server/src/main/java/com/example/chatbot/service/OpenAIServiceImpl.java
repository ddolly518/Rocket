package com.example.chatbot.service;

import com.example.chatbot.dto.OpenAIChatRequest;
import com.example.chatbot.dto.OpenAIChatResponse;
import com.example.chatbot.dto.OpenAIMessage;
import com.example.chatbot.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

// 외부 OpenAI와 통신하는 전담 클래스
@Service
@RequiredArgsConstructor
public class OpenAIServiceImpl implements OpenAIService {

    private final WebClient openAiWebClient;

    @Value("${openai.model}")
    private String model;

    /*@Override
    public Flux<String> chatStream(String message, Long conversationId) {
        // WebClient를 사용한 스트리밍 구현
        return webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(Map.of(
                        "model", "gpt-3.5-turbo",
                        "messages", List.of(Map.of("role", "user", "content", message)),
                        "stream", true
                ))
                .retrieve()
                .bodyToFlux(String.class)
                .map(chunk -> {
                    // SSE 형식으로 변환
                    return "data: " + chunk + "\n\n";
                });
    }*/

    @Override
    public String chat(List<Message> contextMessages, String systemPrompt) {

        // 1. 대화 컨텍스트 → OpenAI messages 변환
        List<OpenAIMessage> messages = new ArrayList<>();
        messages.add(new OpenAIMessage("system", systemPrompt));
        messages.addAll(contextMessages.stream()
                .map(m -> new OpenAIMessage(m.getRole().value(), m.getContent()))
                .toList());

        // 2. OpenAI 요청 생성
        OpenAIChatRequest request = new OpenAIChatRequest(
                model,
                messages,
                0.7
        );

        // 3. OpenAI API 호출
        OpenAIChatResponse response = openAiWebClient.post()
                .uri("/v1/chat/completions")
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(body -> new RuntimeException("OpenAI error: " + body))
                )
                .bodyToMono(OpenAIChatResponse.class)
                .block();

        // 4. 응답 파싱
        if (response == null || response.getChoices().isEmpty()) {
            throw new IllegalStateException("OpenAI response is empty");
        }

        return response.getChoices()
                .getFirst()
                .getMessage()
                .getContent();
    }
}