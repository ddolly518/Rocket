package com.example.chatbot.service;

import com.example.chatbot.dto.LoginRequest;
import com.example.chatbot.dto.LoginResponse;
import com.example.chatbot.dto.SignupRequest;
import com.example.chatbot.dto.SignupResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    SignupResponse signup(SignupRequest request);
    LoginResponse login(LoginRequest request, HttpServletResponse httpResponse);
    void reissue(HttpServletRequest request, HttpServletResponse response);
    void logout(HttpServletResponse response);
}
