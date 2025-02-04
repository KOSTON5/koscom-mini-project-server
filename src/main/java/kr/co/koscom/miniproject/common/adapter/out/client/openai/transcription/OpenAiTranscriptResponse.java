package kr.co.koscom.miniproject.common.adapter.out.client.openai.transcription;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OpenAiTranscriptResponse {

    @JsonProperty("text")
    private String text;

    public OpenAiTranscriptResponse(String text) {
        this.text = text;
    }
}
