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
import com.example.chatbot.gpt.SummaryService;
import com.example.chatbot.gpt.TokenUtils;
import com.example.chatbot.repository.ConversationRepository;
import com.example.chatbot.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final TokenUtils tokenUtils;
    private final SummaryService summaryService;

    private static final int MAX_INPUT_TOKENS = 6000;
    private static final int RESERVED_OUTPUT_TOKENS = 1000;

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

        // 4. LLM 메시지 생성
        List<Message> finalContext = new ArrayList<>();
        String aiResponse;

        if (chatPrompt == ChatPrompt.REPAIR) {
            finalContext.addAll(contextMessages);

            // 5. OpenAI API 호출
            aiResponse = openAIService.chat(finalContext, ChatPrompt.REPAIR);
            aiResponse = repairChatResponseHandler.handle(aiResponse);
        } else {
            // 토큰 기반 trimming
            List<Message> trimmed = trim(contextMessages);

            // summary 업데이트 필요 여부 확인
            // 토큰 한도를 넘어서 일부 메시지가 잘려 크기가 같지 않음
            if (trimmed.size() < contextMessages.size()) {
                List<Message> removed = contextMessages.subList(0, contextMessages.size()- trimmed.size());
                String newSummary = summaryService.summarize(conversation.getSummary(), removed);
                conversation.updateSummary(newSummary);
            }

            // 요약 메시지 추가
            if (conversation.getSummary()!=null && !conversation.getSummary().isBlank()) {
                finalContext.add(Message.builder()
                        .role(Role.ASSISTANT)
                        .content("Conversation summary:\n" + conversation.getSummary())
                        .build());
            }
            finalContext.addAll(trimmed);

            // 5. OpenAI API 호출
            aiResponse = openAIService.chat(finalContext, ChatPrompt.GENERAL);
        }

        // 6. AI 응답 저장
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

        getAuthorizedConversation(id);

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

    // LLM 입력 토큰 한도를 넘지 않도록 최근 메시지만 남기는 슬라이딩 윈도우 로직
    private List<Message> trim(List<Message> messages) {

        int maxTokens = MAX_INPUT_TOKENS - RESERVED_OUTPUT_TOKENS; // 최대 입력 토큰
        List<Message> result = new ArrayList<>(); // 결과 리스트 생성
        int total = 0; // 현재까지 누적된 토큰 수

        // 최신 메시지부터 검사
        for (int i = messages.size() - 1; i >= 0; i--) {

            Message m = messages.get(i);

            // 실제 텍스트 토큰 수 계산 +4는 role
            int tokens = tokenUtils.count(m.getContent()) + 4;

            // 한도 초과 시 중단
            if (total + tokens > maxTokens) break;

            result.addFirst(m);
            total += tokens;
        }

        return result;
    }
}