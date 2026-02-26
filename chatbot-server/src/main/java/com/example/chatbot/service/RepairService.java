package com.example.chatbot.service;

import com.example.chatbot.dto.RepairRequest;
import com.example.chatbot.dto.RepairResponse;

import java.util.List;

public interface RepairService {
    RepairResponse createRepair(RepairRequest request);
    List<RepairResponse> getAllRepairs();
}
