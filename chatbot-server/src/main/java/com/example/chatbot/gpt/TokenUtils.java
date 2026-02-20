package com.example.chatbot.gpt;

import com.example.chatbot.exception.CustomErrorCode;
import com.example.chatbot.exception.CustomException;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 모델의 토큰 한도를 정확히 계산하기 위한 도구
@Component
public class TokenUtils {

    private final Encoding encoding;

    public TokenUtils(@Value("${openai.model}") String model) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        this.encoding = registry
                .getEncodingForModel(model)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MODEL_NOT_FOUND));
    }

    public int count(String text) {
        if (text == null || text.isBlank()) return 0;
        return encoding.countTokens(text);
    }
}
