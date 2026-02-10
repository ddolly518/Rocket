package com.example.chatbot.dto;

import com.example.chatbot.entity.Conversation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "대화 상세 조회 DTO")
public class ConversationDetailDto {

    private Long id;
    private String title;
    private long count;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ConversationDetailDto from(Conversation conversation, long count) {
        return ConversationDetailDto.builder()
                .id(conversation.getId())
                .title(conversation.getTitle())
                .count(count)
                .createdAt(conversation.getCreatedAt())
                .updatedAt(conversation.getUpdatedAt())
                .build();
    }
}
