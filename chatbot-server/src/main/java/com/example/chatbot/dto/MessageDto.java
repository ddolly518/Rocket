package com.example.chatbot.dto;

import com.example.chatbot.entity.Message;
import com.example.chatbot.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 서버 -> 클라이언트 응답
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "대화 메시지 DTO")
public class MessageDto {

    @Schema(description = "메시지 ID", example = "123")
    private Long id;

    @Schema(description = "대화 ID", example = "456")
    private Long conversationId;

    @Schema(description = "보낸 이", example = "assistant", allowableValues = {"user", "assistant"})
    private String role;

    @Schema(description = "메시지 내용", example = "안녕하세요!")
    private String content;

    @Schema(description = "메시지 생성 시간", example = "2026-02-10T15:30:00")
    private LocalDateTime createdAt;

    public static MessageDto from(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .conversationId(message.getConversation().getId())
                .role(message.getRole().value())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
