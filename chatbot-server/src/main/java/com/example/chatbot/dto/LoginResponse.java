package com.example.chatbot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 로그인 응답 DTO")
public record LoginResponse  (

        @Schema(description = "사용자 이메일", example = "user@example.com")
        String email,

        @Schema(description = "사용자 닉네임", example = "user")
        String nickname,

        @Schema(description = "로그인 성공 메시지", example = "로그인에 성공했습니다.")
        String message
) {}
