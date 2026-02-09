package com.example.chatbot.dto;

import com.example.chatbot.entity.Message;
import com.example.chatbot.entity.Role;
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
public class MessageDto {

    private Long id;
    private Long conversationId;
    private String role;
    private String content;
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
