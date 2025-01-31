package kr.co.koscom.miniproject.adapter.out.client.openai;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder(toBuilder = true)
public record OpenAiRequest(
    String model,
    List<Map<String, String>> messages,
    double temperature
) {

    private OpenAiRequest(final String text) {
        this("gpt-4o-mini",
            List.of(Map.of("role", "user", "content", text)),
            0.7
        );
    }

    public static OpenAiRequest from(final String text) {
        return new OpenAiRequest(text);
    }
}
