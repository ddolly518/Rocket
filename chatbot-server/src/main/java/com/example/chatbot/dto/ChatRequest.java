package com.example.chatbot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 사용자 -> 서버
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "사용자 채팅 요청 DTO")
public class ChatRequest {

    @Schema(description = "대화 ID", example = "123")
    private Long conversationId;

    @NotBlank(message = "message는 필수입니다.")
    @Schema(description = "사용자가 보낸 메시지 내용", example = "안녕하세요!")
    private String message;
}