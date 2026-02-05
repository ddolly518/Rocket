package com.example.chatbot.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

// JWT 필터 : 요청에 담긴 JWT를 검사해서 로그인된 사용자로 만들어주는 필터
@Component // 스프링 컨테이너에 Bean으로 등록
@RequiredArgsConstructor // 의존성 주입(DI)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // JWT 생성/검증
    private final JwtTokenProvider tokenProvider;

    // 허용 목록
    private static final String[] WHITE_LIST = {
            // Auth & Admin login endpoints
            "/api/auth",
            // Public resources & docs
            "/swagger-ui", "/v3/api-docs",
            "/webjars"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // 화이트리스트 경로면 바로 통과
        for (String exclude : WHITE_LIST) {
            if (path.startsWith(exclude)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 요청에서 토큰 추출
        String token = resolveToken(request);

        // 유효성 검사 → 사용자 인증 객체 생성 → 로그인된 사용자
        if(token != null && tokenProvider.validateToken(token)) {
            // 사용자 식별
            String email = tokenProvider.getEmail(token);
            List<String> roles = tokenProvider.getRoles(token);

            Collection<? extends GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            // 인증 객체 생성
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            email, null, authorities);

            // Security Context에 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 요청 전달
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        // 이름이 accessToken인 쿠키를 찾아 JWT 문자열 반환
        Cookie[] cookies = request.getCookies();
        if (cookies != null) { // null 체크 추가
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
