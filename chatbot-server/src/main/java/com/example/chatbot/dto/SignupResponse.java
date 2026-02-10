package com.example.chatbot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 응답 DTO")
public record SignupResponse (

        @Schema(description = "가입된 사용자 이메일", example = "user@example.com")
        String email,

        @Schema(description = "가입된 사용자 닉네임", example = "user")
        String nickname,

        @Schema(description = "회원가입 성공 메시지", example = "회원가입에 성공했습니다.")
        String message
) {}
