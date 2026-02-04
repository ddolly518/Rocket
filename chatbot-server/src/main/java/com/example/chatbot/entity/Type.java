package com.example.chatbot.entity;

import lombok.Getter;

@Getter
public enum Type {
    USER("일반 사용자"),
    ADMIN("관리자");

    private final String label;

    private Type(String label) {
        this.label = label;
    }
}