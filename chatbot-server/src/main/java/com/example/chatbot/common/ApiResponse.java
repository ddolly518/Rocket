package com.example.chatbot.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 전역 응답 규격
@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private T data;

    // 성공 응답
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data);
    }
}
