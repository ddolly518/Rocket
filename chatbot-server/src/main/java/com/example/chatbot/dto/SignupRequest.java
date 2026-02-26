package com.example.chatbot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "회원가입 요청 DTO")
public record SignupRequest(

        @Schema(description = "사용자 이메일", example = "user@example.com")
        @Email @NotBlank String email,

        @Schema(description = "사용자 비밀번호", example = "password")
        @NotBlank String password,

        @Schema(description = "재확인 비밀번호", example = "password")
        @NotBlank String confirmPassword,

        @Schema(description = "사용자 닉네임", example = "user")
        @NotBlank String nickname,

        @Schema(description = "사용자 권한", example = "USER", allowableValues = {"USER", "ADMIN"})
        @NotBlank String type
) {}