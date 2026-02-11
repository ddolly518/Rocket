package com.example.chatbot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "수리 접수 완료 DTO")
public record RepairResponse (

    @Schema(description = "접수 아이디", example = "123")
    long id,

    @Schema(description = "접수 제품", example = "휴대폰")
    String product
) {}
