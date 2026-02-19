package com.example.chatbot.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

// API 문서 자동 생성
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        // API에 표시될 제목, 설명, 버전
                        .title("AI Chatbot API")
                        .description("GPT 기반 챗봇 API 서버")
                        .version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(
                        new Components()
                                // Authorization 헤더에 JWT 토큰을 넣도록 설정
                                .addSecuritySchemes("JWT",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                                .name(HttpHeaders.AUTHORIZATION)
                                )
                                // 공통 오류 스키마 정의
                                .addResponses("Unauthorized", new ApiResponse().description("인증 실패"))
                                .addResponses("InternalError", new ApiResponse().description("서버 오류"))
                                .addResponses("Success", new ApiResponse().description("성공"))
                );
    }
}