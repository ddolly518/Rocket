package com.example.chatbot.service;

import com.example.chatbot.dto.RepairRequest;
import com.example.chatbot.dto.RepairResponse;
import com.example.chatbot.entity.Repair;
import com.example.chatbot.entity.RepairStatus;
import com.example.chatbot.entity.User;
import com.example.chatbot.repository.RepairRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepairServiceImpl implements RepairService {

    private final RepairRepository repairRepository;
    private final UserService userService;

    @Override
    @Transactional
    public RepairResponse createRepair(RepairRequest request) {

        User currentUser = userService.getCurrentUser();

        Repair repair = Repair.builder()
                .user(currentUser)
                .status(RepairStatus.REQUESTED)
                .product(request.product())
                .issue(request.issue())
                .build();

        Repair saved = repairRepository.save(repair);

        return new RepairResponse(saved.getId(), saved.getProduct().getLabel());
    }
}
