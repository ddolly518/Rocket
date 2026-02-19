package com.example.chatbot.service;

import com.example.chatbot.dto.ChatRequest;
import com.example.chatbot.dto.ConversationDetailDto;
import com.example.chatbot.dto.ConversationSummaryDto;
import com.example.chatbot.dto.MessageDto;
import com.example.chatbot.entity.Conversation;
import com.example.chatbot.entity.Message;
import com.example.chatbot.entity.Role;
import com.example.chatbot.entity.User;
import com.example.chatbot.exception.CustomErrorCode;
import com.example.chatbot.exception.CustomException;
import com.example.chatbot.gpt.ChatPrompt;
import com.example.chatbot.gpt.RepairChatResponseHandler;
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
    private final RepairChatResponseHandler repairChatResponseHandler;

    // 메시지 전송 및 응답
    @Override
    @Transactional
    public MessageDto processChat(ChatRequest request, ChatPrompt chatPrompt) {

        User currentUser = userService.getCurrentUser();

        // 1. 대화 조회 또는 생성
        Conversation conversation;
        if (request.conversationId() != null) {
            conversation = getAuthorizedConversation(request.conversationId());
        } else {
            conversation = Conversation.builder()
                    .user(currentUser)
                    .title(request.message().substring(0, Math.min(50, request.message().length())))
                    .build();
            conversation = conversationRepository.save(conversation);
        }

        // 2. 사용자 메시지 저장
        Message userMessage = Message.builder()
                .conversation(conversation)
                .role(Role.USER)
                .content(request.message())
                .build();
        messageRepository.save(userMessage);

        // 3. 이전 대화 컨텍스트 조회 (최근 10개)
        List<Message> contextMessages = messageRepository
                .findTop10ByConversationIdOrderByCreatedAtDesc(conversation.getId());
        Collections.reverse(contextMessages);

        // 4. OpenAI API 호출
        String systemPrompt = chatPrompt == ChatPrompt.REPAIR ? ChatPrompt.REPAIR.value() : ChatPrompt.GENERAL.value();
        String aiResponse = openAIService.chat(contextMessages, systemPrompt);
        if (chatPrompt == ChatPrompt.REPAIR) {
            aiResponse = repairChatResponseHandler.handle(aiResponse);
        }

        // 5. AI 응답 저장
        Message assistantMessage = Message.builder()
                .conversation(conversation)
                .role(Role.ASSISTANT)
                .content(aiResponse)
                .build();
        messageRepository.save(assistantMessage);

        return MessageDto.from(assistantMessage);
    }

    // 대화 목록 조회
    @Override
    public List<ConversationSummaryDto> getAllConversations() {

        User currentUser = userService.getCurrentUser();

        return conversationRepository.findAllByUserOrderByCreatedAtDesc(currentUser)
                .stream()
                .map(ConversationSummaryDto::from)
                .toList();
    }

    // 대화 상세 조회
    @Override
    public ConversationDetailDto getConversationById(Long id) {

        Conversation conversation = getAuthorizedConversation(id);
        long count = messageRepository.countByConversationId(id);

        return ConversationDetailDto.from(conversation, count);
    }

    // 대화 내 메시지 조회
    @Override
    public List<MessageDto> getMessagesByConversationId(Long id) {

        Conversation conversation = getAuthorizedConversation(id);

        return messageRepository.findAllByConversationIdOrderByCreatedAtAsc(id)
                .stream()
                .map(MessageDto::from)
                .toList();
    }

    // 대화 삭제
    @Override
    @Transactional
    public void deleteConversation(Long id) {

        Conversation conversation = getAuthorizedConversation(id);

        messageRepository.deleteAllByConversationId(id);
        conversationRepository.delete(conversation);
    }

    /*@Override
    public Flux<String> chatStream(String message, Long conversationId) {
        openAIService.chatStream(message, conversationId);
    }*/

    // 대화 소유권 검증
    private Conversation getAuthorizedConversation(Long conversationId) {

        User currentUser = userService.getCurrentUser();

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.CONVERSATION_NOT_EXIST));

        if (!conversation.getUser().getId().equals(currentUser.getId())) {
            throw new CustomException(CustomErrorCode.UNAUTHORIZED);
        }

        return conversation;
    }
}