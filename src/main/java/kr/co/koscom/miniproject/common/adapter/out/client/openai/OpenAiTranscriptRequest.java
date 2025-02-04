package kr.co.koscom.miniproject.common.adapter.out.client.openai;

import lombok.Builder;

@Builder
public record OpenAiTranscriptRequest(
    String model
) {
    public static OpenAiTranscriptRequest defaultRequest() {
        return new OpenAiTranscriptRequest("whisper-1"); // OpenAI의 Whisper 모델
    }
}
