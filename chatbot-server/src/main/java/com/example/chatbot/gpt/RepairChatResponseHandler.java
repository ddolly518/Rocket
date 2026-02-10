package com.example.chatbot.gpt;

import com.example.chatbot.dto.GptActionResponse;
import com.example.chatbot.dto.RepairRequest;
import com.example.chatbot.dto.RepairResponse;
import com.example.chatbot.entity.RepairProduct;
import com.example.chatbot.service.RepairService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepairChatResponseHandler {

    private final RepairService repairService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String handle(String aiResponse) {

        try {
            // JSON 파싱
            GptActionResponse response =
                    objectMapper.readValue(aiResponse, GptActionResponse.class);

            if ("CREATE_REPAIR".equals(response.action())) {

                RepairRequest request = new RepairRequest(
                        RepairProduct.valueOf(response.product()),
                        response.issue()
                );

                repairService.createRepair(request);

                return "수리 접수가 완료되었습니다. 접수 내역을 확인해주세요.";
            }

        } catch (JsonProcessingException e) {
            // 아직 질문 단계 → 그대로 GPT 응답 반환
            return aiResponse;
        } catch (Exception e) {
            throw new IllegalStateException("GPT 수리 접수 응답 파싱 실패", e);
        }

        return aiResponse;
    }
}