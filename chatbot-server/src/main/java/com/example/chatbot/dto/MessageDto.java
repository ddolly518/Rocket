package com.example.chatbot.dto;

import com.example.chatbot.entity.Message;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

// 서버 -> 클라이언트 응답
@Schema(description = "대화 메시지 DTO")
public record MessageDto (
        @Schema(description = "메시지 ID", example = "123")
        Long id,

        @Schema(description = "대화 ID", example = "456")
        Long conversationId,

        @Schema(description = "보낸 이", example = "assistant", allowableValues = {"user", "assistant"})
        String role,

        @Schema(description = "메시지 내용", example = "안녕하세요!")
        String content,

        @Schema(description = "메시지 생성 시간", example = "2026-02-10T15:30:00")
        LocalDateTime createdAt
) {
    public static MessageDto from(Message message) {
        return new MessageDto(
                message.getId(),
                message.getConversation().getId(),
                message.getRole().value(),
                message.getContent(),
                message.getCreatedAt()
        );
    }
}
