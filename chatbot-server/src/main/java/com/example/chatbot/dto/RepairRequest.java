package com.example.chatbot.dto;

import com.example.chatbot.entity.RepairProduct;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "수리 접수 요청 DTO")
public record RepairRequest (

    @Schema(description = "수리 접수 제품", example = "PHONE")
    RepairProduct product,

    @Schema(description = "수리 접수 내용", example = "전원이 안켜져요!")
    String issue
) {}
