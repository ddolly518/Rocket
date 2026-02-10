package com.example.chatbot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

// 사용자 -> 서버
@Schema(description = "사용자 채팅 요청 DTO")
public record ChatRequest (

    @Schema(description = "대화 ID", example = "123")
    Long conversationId,

    @NotBlank(message = "message는 필수입니다.")
    @Schema(description = "사용자가 보낸 메시지 내용", example = "안녕하세요!")
    String message
) {}