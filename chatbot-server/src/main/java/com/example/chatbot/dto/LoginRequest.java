package com.example.chatbot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "사용자 로그인 요청 DTO")
public record LoginRequest(

        @Schema(description = "사용자 이메일", example = "user@example.com")
        @NotBlank String email,

        @Schema(description = "사용자 비밀번호", example = "password")
        @NotBlank String password
) {}
