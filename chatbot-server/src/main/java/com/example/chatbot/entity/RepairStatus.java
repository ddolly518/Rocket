package com.example.chatbot.entity;

import lombok.Getter;

@Getter
public enum RepairStatus {
    REQUESTED("요청됨"),
    IN_PROGRESS("진행중"),
    COMPLETED("완료"),
    CANCELED("취소");

    private final String label;

    private RepairStatus(String label) {
        this.label = label;
    }
}
