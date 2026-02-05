package com.example.chatbot.config;

import com.example.chatbot.security.CustomAuthenticationEntryPoint;
import com.example.chatbot.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

// Spring Security 설정, 필터 체인, URL권한, JWT필터 등록 등
@Configuration // 스프링 설정 클래스
@EnableWebSecurity // Spring Security 활성화
@RequiredArgsConstructor // 의존성 주입(DI)
public class SecurityConfig {

    // JWT 필터 : 요청에 담긴 JWT를 검사해서 로그인된 사용자로 만들어주는 필터
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    // 인증 실패 시 예외 처리
    private final CustomAuthenticationEntryPoint entryPoint;

    // Spring Bean 등록
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {

        // 필터 체인 : 요청이 컨트롤러에 도달하기 전에 반드시 거치는 보안 검사 라인
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 필터 활성화
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 무상태 설정

                // URL권한 : URL에 누가 접근할 수 있는지 정하는 규칙
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/api/auth/**"
                        ).permitAll() // 로그인 없이 누구나 접근 가능
                        .anyRequest().authenticated() // 나머지 전부 인증 필요
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint)) // 인증 실패 시 예외 처리
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT 필터 등록

        return http.build();
    }

    // 비밀번호 암호화 위한 BCrypt 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 인증을 위한 AuthenticationManager 빈 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // CORS(브라우저 보안 규칙) 정책 정의 빈 등록
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of(
                "http://localhost:5173" // 프론트 도메인
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS")); // 허용 HTTP 메서드
        config.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
        config.setAllowCredentials(true); // 인증정보 포함 허용

        // 모든 URL요청에 대해서 CORS규칙 적용함
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
