package kr.co.koscom.miniproject.common.adapter.out.client.openai.analyzation;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder(toBuilder = true)
public record OpenAiAnalyzeTextRequest(
    String model,
    List<Map<String, String>> messages,
    double temperature
) {

    private OpenAiAnalyzeTextRequest(final String text) {
        this(
            "gpt-4o-mini",
            List.of(
	Map.of(
	    "role", "system",
	    "content",
	    "You are a specialized AI assistant designed to output plain JSON. "
	),
	Map.of(
	    "role", "user",
	    "content", text
	)
            ),
            0.7
        );
    }

    public static OpenAiAnalyzeTextRequest from(final String text) {
        return new OpenAiAnalyzeTextRequest(text);
    }
}
