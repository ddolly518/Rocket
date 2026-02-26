package com.example.chatbot.gpt;

import com.example.chatbot.entity.Message;
import com.example.chatbot.entity.Role;
import com.example.chatbot.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final OpenAIService openAIService;

    public String summarize(String previousSummary, List<Message> removedMessages) {

        String removedText = removedMessages.stream()
                .map(m -> m.getRole() + ": " + m.getContent())
                .collect(Collectors.joining("\n"));

        String prompt = ChatPrompt.SUMMARIZE.value().formatted(previousSummary == null ? "" : previousSummary, removedText);

        return openAIService.chat(List.of(Message.builder()
                        .role(Role.USER)
                        .content(prompt)
                .build()), ChatPrompt.SUMMARIZE);
    }
}