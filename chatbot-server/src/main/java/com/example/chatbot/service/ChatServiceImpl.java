package com.example.chatbot.service;

import com.example.chatbot.dto.ChatRequest;
import com.example.chatbot.dto.ConversationDto;
import com.example.chatbot.dto.MessageDto;
import com.example.chatbot.entity.Conversation;
import com.example.chatbot.entity.Message;
import com.example.chatbot.entity.Role;
import com.example.chatbot.entity.User;
import com.example.chatbot.exception.CustomErrorCode;
import com.example.chatbot.exception.CustomException;
import com.example.chatbot.repository.ConversationRepository;
import com.example.chatbot.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final OpenAIService openAIService;
    private final UserService userService;

    @Transactional
    public MessageDto processChat(ChatRequest request) {
        User currentUser = userService.getCurrentUser();

        // 1. 대화 조회 또는 생성
        Conversation conversation;
        if (request.getConversationId() != null) {
            conversation = conversationRepository.findById(request.getConversationId())
                    .orElseThrow(() -> new CustomException(CustomErrorCode.CONVERSATION_NOT_EXIST));

            // 대화 소유권 검증
            if (!conversation.getUser().getId().equals(currentUser.getId())) {
                throw new CustomException(CustomErrorCode.UNAUTHORIZED);
            }
        } else {
            conversation = Conversation.builder()
                    .user(currentUser)
                    .title(request.getMessage().substring(0, Math.min(50, request.getMessage().length())))
                    .build();
            conversation = conversationRepository.save(conversation);
        }

        // 2. 사용자 메시지 저장
        Message userMessage = Message.builder()
                .conversation(conversation)
                .role(Role.USER)
                .content(request.getMessage())
                .build();
        messageRepository.save(userMessage);

        // 3. 이전 대화 컨텍스트 조회 (최근 10개)
        List<Message> contextMessages = messageRepository
                .findTop10ByConversationIdOrderByCreatedAtDesc(conversation.getId());
        Collections.reverse(contextMessages);

        // 4. OpenAI API 호출
        String aiResponse = openAIService.chat(contextMessages);

        // 5. AI 응답 저장
        Message assistantMessage = Message.builder()
                .conversation(conversation)
                .role(Role.ASSISTANT)
                .content(aiResponse)
                .build();
        messageRepository.save(assistantMessage);

        return MessageDto.from(assistantMessage);
    }

    @Override
    public List<ConversationDto> getAllConversations() {
        return List.of();
    }

    @Override
    public ConversationDto getConversationById(Long id) {
        return null;
    }

    @Override
    public List<MessageDto> getMessagesByConversationId(Long id) {
        return List.of();
    }

    @Override
    public void deleteConversation(Long id) {

    }

    /*@Override
    public Flux<String> chatStream(String message, Long conversationId) {
        openAIService.chatStream(message, conversationId);
    }*/
}