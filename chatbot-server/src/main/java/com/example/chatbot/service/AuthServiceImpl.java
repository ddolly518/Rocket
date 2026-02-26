package com.example.chatbot.service;

import com.example.chatbot.dto.LoginRequest;
import com.example.chatbot.dto.LoginResponse;
import com.example.chatbot.dto.SignupRequest;
import com.example.chatbot.dto.SignupResponse;
import com.example.chatbot.entity.Type;
import com.example.chatbot.entity.User;
import com.example.chatbot.exception.CustomErrorCode;
import com.example.chatbot.exception.CustomException;
import com.example.chatbot.repository.UserRepository;
import com.example.chatbot.security.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new CustomException(CustomErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (!request.password().equals(request.confirmPassword())) {
            throw new CustomException(CustomErrorCode.PASSWORD_NOT_MATCH);
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        Type type = Type.fromCode(request.type());

        User user = User.builder()
                .email(request.email())
                .password(encodedPassword)
                .nickname(request.nickname())
                .type(type)
                .build();

        userRepository.save(user);
        return new SignupResponse(request.email(), request.nickname(), "회원가입에 성공했습니다.");
    }

    @Override
    public LoginResponse login(LoginRequest request, HttpServletResponse httpResponse) {
        // 회원 조회
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_EXIST));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new CustomException(CustomErrorCode.PASSWORD_NOT_MATCH);
        }

        // JWT 생성
        String accessToken = tokenProvider.createAccessToken(user.getEmail(), user.getType());
        String refreshToken = tokenProvider.createRefreshToken(user.getEmail());

        // Redis에 refreshToken 저장
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("refreshToken:"+user.getEmail(), refreshToken, Duration.ofMillis(tokenProvider.getRefreshTokenValidity()));

        // 쿠키 설정
        httpResponse.addCookie(createAccessTokenCookie(accessToken));
        httpResponse.addCookie(createRefreshTokenCookie(refreshToken));

        return new LoginResponse(user.getEmail(), user.getNickname(), "로그인에 성공했습니다.");
    }

    private Cookie createAccessTokenCookie(String token) {
        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // https 환경
        cookie.setPath("/");
        cookie.setMaxAge((int) (tokenProvider.getAccessTokenValidity() / 1000)); // 1시간
        cookie.setAttribute("SameSite", "None");
        return cookie;
    }

    private Cookie createRefreshTokenCookie(String token) {
        Cookie cookie = new Cookie("refreshToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (tokenProvider.getRefreshTokenValidity() / 1000)); // 7일
        cookie.setAttribute("SameSite", "None");
        return cookie;
    }

    @Override
    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshToken(request);

        if (refreshToken == null ||
                !tokenProvider.validateToken(refreshToken) ||
                !tokenProvider.isRefreshToken(refreshToken)) {
            throw new CustomException(CustomErrorCode.INVALID_TOKEN);
        }

        String email = tokenProvider.getEmail(refreshToken);

        // Redis에서 RefreshToken 확인
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String storedToken = ops.get("refreshToken:" + email);
        if (!refreshToken.equals(storedToken)) {
            throw new CustomException(CustomErrorCode.INVALID_TOKEN);
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_EXIST));

        String newAccessToken =
                tokenProvider.createAccessToken(user.getEmail(), user.getType());

        response.addCookie(createAccessTokenCookie(newAccessToken));
    }

    // refreshToken 추출
    private String extractRefreshToken(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("refreshToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    @Override
    public void logout(HttpServletResponse response) {

        User currentUser = userService.getCurrentUser();

        // Redis에서 RefreshToken 삭제
        redisTemplate.delete("refreshToken:" + currentUser.getEmail());

        // accessToken 쿠키 삭제
        Cookie accessToken = new Cookie("accessToken", null);
        accessToken.setHttpOnly(true);
        accessToken.setSecure(true); // https 환경이면 true
        accessToken.setPath("/");
        accessToken.setMaxAge(0); // 만료시간 0으로 설정 → 삭제
        accessToken.setAttribute("SameSite", "None");

        // refreshToken 쿠키 삭제
        Cookie refreshToken = new Cookie("refreshToken", null);
        refreshToken.setHttpOnly(true);
        refreshToken.setSecure(true);
        refreshToken.setPath("/auth/refresh");
        refreshToken.setMaxAge(0);
        refreshToken.setAttribute("SameSite", "None");

        response.addCookie(accessToken);
        response.addCookie(refreshToken);
    }
}
