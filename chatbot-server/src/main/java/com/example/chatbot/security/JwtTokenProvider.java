package com.example.chatbot.security;

import com.example.chatbot.entity.Type;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

// JWT 생성/검증
@Component // 스프링 컨테이너에 Bean으로 등록
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-validity}")
    private long ACCESS_TOKEN_VALIDITY;

    @Value("${jwt.refresh-token-validity}")
    private long REFRESH_TOKEN_VALIDITY;

    private Key key;

    public static final String TOKEN_TYPE = "tokenType";
    public static final String ACCESS = "ACCESS";
    public static final String REFRESH = "REFRESH";
    public static final String ROLES = "roles";

    @PostConstruct
    public void init() {
        // secretKey를 HMAC SHA256용 Key로 초기화
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // AccessToken 생성
    public String createAccessToken(String email, Type type) {

        return createToken(email, ACCESS_TOKEN_VALIDITY, ACCESS, List.of(type.name()));
    }

    // RefreshToken 생성
    public String createRefreshToken(String email) {

        return createToken(email, REFRESH_TOKEN_VALIDITY, REFRESH, null);
    }

    private String createToken(String email, long expiration, String tokenType, List<String> roles) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        JwtBuilder builder = Jwts.builder()
                .subject(email)
                .claim(TOKEN_TYPE, tokenType)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256);

        // AccessToken에만 권한 포함
        if (ACCESS.equals(tokenType) && roles != null) {
            builder.claim(ROLES, roles);
        }

        return builder.compact();
    }

    // JWT 검증
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // JWT에서 email 추출
    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    // JWT에서 tokenType 추출
    public String getTokenType(String token) {
        return getClaims(token).get(TOKEN_TYPE, String.class);
    }

    // JWT에서 type 추출
    public List<String> getRoles(String token) {
        return getClaims(token).get(ROLES, List.class);
    }

    public long getAccessTokenValidity() {
        return ACCESS_TOKEN_VALIDITY;
    }

    public long getRefreshTokenValidity() {
        return REFRESH_TOKEN_VALIDITY;
    }

    // AccessToken 여부 확인
    public boolean isAccessToken(String token) {
        return ACCESS.equals(getTokenType(token));
    }

    // RefreshToken 여부 확인
    public boolean isRefreshToken(String token) {
        return REFRESH.equals(getTokenType(token));
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
