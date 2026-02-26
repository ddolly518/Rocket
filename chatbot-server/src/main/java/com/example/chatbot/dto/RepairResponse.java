package com.example.chatbot.dto;

import com.example.chatbot.entity.Repair;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "수리 접수 완료 DTO")
public record RepairResponse (

    @Schema(description = "접수 아이디", example = "123")
    long id,

    LocalDateTime createdAt,

    String issue,

    @Schema(description = "접수 제품", example = "휴대폰")
    String product,

    String status
) {
    public static RepairResponse from(Repair repair) {
        return new RepairResponse(
                repair.getId(),
                repair.getCreatedAt(),
                repair.getIssue(),
                repair.getProduct().getLabel(),
                repair.getStatus().getLabel()
        );
    }
}
