package com.example.chatbot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    // 회원 M
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "M001", "존재하지 않는 회원입니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "M002", "아이디 또는 비밀번호가 틀렸습니다"),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "M003", "이미 가입된 이메일입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "M004", "유효하지 않거나 만료된 토큰입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "M005", "인증이 필요합니다."),

    // 대화 C
    CONVERSATION_NOT_EXIST(HttpStatus.BAD_REQUEST, "C001", "존재하지 않는 대화입니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
