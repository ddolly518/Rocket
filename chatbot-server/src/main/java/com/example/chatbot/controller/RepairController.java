package com.example.chatbot.controller;

import com.example.chatbot.common.ApiResponse;
import com.example.chatbot.dto.RepairResponse;
import com.example.chatbot.service.RepairService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RepairController {

    private final RepairService repairService;

    // 수리접수 목록 조회
    @Operation(summary = "수리접수 목록 조회", description = "사용자의 전체 수리접수 목록을 조회합니다.")
    @GetMapping("/repair")
    public ResponseEntity<ApiResponse<List<RepairResponse>>> getRepairss() {
        List<RepairResponse> repairs = repairService.getAllRepairs();
        return ResponseEntity.ok(ApiResponse.success(repairs));
    }
}
