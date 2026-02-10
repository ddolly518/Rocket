package com.example.chatbot.controller;

import com.example.chatbot.dto.ChatRequest;
import com.example.chatbot.dto.ConversationDto;
import com.example.chatbot.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import com.example.chatbot.common.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.chatbot.dto.MessageDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatController {

    private final ChatService chatService;

    // 메시지 전송 및 응답
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "대화 존재하지 않음: C001")
    })
    @Operation(summary = "채팅 메시지 전송", description = "사용자 메시지를 전송하고 AI 응답을 받습니다.")
    @PostMapping("/chat/completions")
    public ResponseEntity<ApiResponse<MessageDto>> chat(@RequestBody ChatRequest request) {
        MessageDto response = chatService.processChat(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 대화 목록 조회
    @Operation(summary = "대화 목록 조회", description = "사용자의 전체 대화 목록을 조회합니다.")
    @GetMapping("/conversations")
    public ResponseEntity<ApiResponse<List<ConversationDto>>> getConversations() {
        List<ConversationDto> conversations = chatService.getAllConversations();
        return ResponseEntity.ok(ApiResponse.success(conversations));
    }

    // 대화 상세 조회
    @Operation(summary = "대화 상세 조회", description = "대화 ID를 기준으로 대화 상세 정보를 조회합니다.")
    @GetMapping("/conversations/{id}")
    public ResponseEntity<ApiResponse<ConversationDto>> getConversation(@PathVariable Long id) {
        ConversationDto conversation = chatService.getConversationById(id);
        return ResponseEntity.ok(ApiResponse.success(conversation));
    }

    // 대화 내 메시지 조회
    @Operation(summary = "대화 메시지 조회", description = "특정 대화에 포함된 모든 메시지를 조회합니다.")
    @GetMapping("/conversations/{id}/messages")
    public ResponseEntity<ApiResponse<List<MessageDto>>> getMessages(@PathVariable Long id) {
        List<MessageDto> messages = chatService.getMessagesByConversationId(id);
        return ResponseEntity.ok(ApiResponse.success(messages));
    }

    // 대화 삭제
    @Operation(summary = "대화 삭제", description = "대화 ID를 기준으로 대화를 삭제합니다.")
    @DeleteMapping("/conversations/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteConversation(@PathVariable Long id) {
        chatService.deleteConversation(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /*@GetMapping(value = "/chat/completions/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestParam String message,
                                   @RequestParam(required = false) Long conversationId) {
        return chatService.chatStream(message, conversationId);
    }*/
}
