package com.example.chatbot.service;

import com.example.chatbot.dto.ChatRequest;
import com.example.chatbot.dto.ConversationDto;
import com.example.chatbot.dto.MessageDto;

import java.util.List;

public interface ChatService {
    MessageDto processChat(ChatRequest request);
    List<ConversationDto> getAllConversations();
    ConversationDto getConversationById(Long id);
    List<MessageDto> getMessagesByConversationId(Long id);
    void deleteConversation(Long id);
}
