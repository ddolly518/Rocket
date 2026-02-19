package com.example.chatbot.dto;

import com.example.chatbot.entity.Conversation;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "대화 상세 조회 DTO")
public record ConversationDetailDto (
        Long id,
        String title,
        long count,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ConversationDetailDto from(Conversation conversation, long count) {
        return new ConversationDetailDto(
                conversation.getId(),
                conversation.getTitle(),
                count,
                conversation.getCreatedAt(),
                conversation.getUpdatedAt()
        );
    }
}
