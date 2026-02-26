package com.example.chatbot.service;

import com.example.chatbot.entity.User;
import com.example.chatbot.exception.CustomErrorCode;
import com.example.chatbot.exception.CustomException;
import com.example.chatbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보 없음
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException(CustomErrorCode.UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();

        // JWT 필터에서 principal = email(String)
        if (!(principal instanceof String email)) {
            throw new CustomException(CustomErrorCode.UNAUTHORIZED);
        }

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new CustomException(CustomErrorCode.USER_NOT_EXIST));
    }
}
