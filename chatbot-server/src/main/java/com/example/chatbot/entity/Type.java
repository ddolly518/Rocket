package com.example.chatbot.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Type {
    ROLE_USER("USER", "일반 사용자"),
    ROLE_ADMIN("ADMIN", "관리자");

    private final String code;
    private final String label;

    Type(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static Type fromCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid type code: " + code)
                );
    }
}