package com.example.chatbot.dto;

import com.example.chatbot.entity.Conversation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "대화 목록 조회 DTO")
public class ConversationSummaryDto {
    private Long id;
    private String title;

    public static ConversationSummaryDto from(Conversation conversation) {
        return ConversationSummaryDto.builder()
                .id(conversation.getId())
                .title(conversation.getTitle())
                .build();
    }
}
