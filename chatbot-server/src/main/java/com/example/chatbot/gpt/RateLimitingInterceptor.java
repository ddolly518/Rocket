package com.example.chatbot.gpt;

import com.example.chatbot.service.RateLimiterService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.ConcurrentHashMap;

// 모든 API 요청을 가로채서 Rate Limiting을 적용
@Component
@RequiredArgsConstructor
public class RateLimitingInterceptor implements HandlerInterceptor {

    private final RateLimiterService rateLimiterService;
    // 사용자별 Bucket 저장
    private final ConcurrentHashMap<String, Bucket> userBuckets = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = "anonymous"; // 인증되지 않은 경우

        if (auth != null && auth.isAuthenticated()) {
            userId = auth.getName(); // JWT에서 가져온 사용자 ID
        }

        // 사용자별 Bucket 가져오기 or 생성
        Bucket bucket = userBuckets.computeIfAbsent(userId, k -> rateLimiterService.createBucket());

        if (!bucket.tryConsume(1)) {
            response.setStatus(429);
            response.getWriter().write("Too Many Requests");
            return false; // 요청 차단
        }

        return true; // 요청 허용
    }
}
