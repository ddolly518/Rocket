package com.example.chatbot.service;

import com.example.chatbot.entity.Message;
import com.example.chatbot.gpt.ChatPrompt;
import reactor.core.publisher.Flux;

import java.util.List;

public interface OpenAIService {
    String chat(List<Message> contextMessages, ChatPrompt systemPrompt);
    Flux<String> chatStream(String message, Long conversationId);
}
