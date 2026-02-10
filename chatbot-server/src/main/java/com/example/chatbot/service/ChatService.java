package com.example.chatbot.service;

import com.example.chatbot.dto.ChatRequest;
import com.example.chatbot.dto.ConversationDetailDto;
import com.example.chatbot.dto.ConversationSummaryDto;
import com.example.chatbot.dto.MessageDto;

import java.util.List;

public interface ChatService {
    MessageDto processChat(ChatRequest request);
    List<ConversationSummaryDto> getAllConversations();
    ConversationDetailDto getConversationById(Long id);
    List<MessageDto> getMessagesByConversationId(Long id);
    void deleteConversation(Long id);
    //Flux<String> chatStream(String message, Long conversationId);
}
