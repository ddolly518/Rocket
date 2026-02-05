package com.example.chatbot.entity;

import lombok.Getter;

@Getter
public enum RepairProduct {
    PHONE("휴대폰"),
    LAPTOP("노트북"),
    TABLET("태블릿"),
    ETC("기타");

    private final String label;

    private RepairProduct(String label) {
        this.label = label;
    }
}
