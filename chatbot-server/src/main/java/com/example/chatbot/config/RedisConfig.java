package com.example.chatbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        String host = System.getenv("REDISHOST");
        int port = Integer.parseInt(System.getenv("REDISPORT"));
        LettuceConnectionFactory factory = new LettuceConnectionFactory(host, port);
        String password = System.getenv("REDISPASSWORD");
        if (password != null && !password.isEmpty()) {
            factory.setPassword(password);
        }
        factory.afterPropertiesSet(); // 중요: 실제 연결 정보 초기화
        return factory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
