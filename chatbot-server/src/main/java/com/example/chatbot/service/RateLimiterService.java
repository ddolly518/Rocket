package com.example.chatbot.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.Duration;

// 토큰 기반 Rate Limiting 정책을 생성
@Service
public class RateLimiterService {

    // 새로운 사용자별 Bucket 생성
    public Bucket createBucket() {
        Bandwidth perSecond = Bandwidth.classic(5, Refill.intervally(5, Duration.ofSeconds(1))); // 1초 5회
        Bandwidth perMinute = Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1))); // 1분 10회
        return Bucket4j.builder()
                .addLimit(perSecond)
                .addLimit(perMinute)
                .build();
    }
}
