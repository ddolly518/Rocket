package com.example.chatbot.service;

import com.example.chatbot.dto.RepairRequest;
import com.example.chatbot.dto.RepairResponse;

public interface RepairService {
    RepairResponse createRepair(RepairRequest request);
}
