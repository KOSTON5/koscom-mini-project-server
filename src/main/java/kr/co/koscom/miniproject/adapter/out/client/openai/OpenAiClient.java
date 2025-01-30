package kr.co.koscom.miniproject.adapter.out.client.openai;

import kr.co.koscom.miniproject.application.dto.response.AnalyzeOrderResponse;
import kr.co.koscom.miniproject.application.port.out.OpenAiClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class OpenAiClient implements OpenAiClientPort<OpenAiRequest, OpenAiResponse> {

    private final WebClient webClient;
    private final String OPENAI_BASE_URL = "https://api.openai.com/v1/chat/completions";
    private final String API_KEY = "sk-proj-mbwKIok9idSsQJ3w7A4DT3BlbkFJX7uCW2CYbnfh9STSFgqz";
    private final String PROMPT = "";

    public OpenAiResponse chat(OpenAiRequest request) {
        return webClient.post()
            .uri(OPENAI_BASE_URL)
            .headers(headers -> {
	headers.set("Content-Type", "application/json");
	headers.set("Authorization", "Bearer " + API_KEY);
            })
            .bodyValue(new OpenAiRequest(PROMPT + request.text()))
            .retrieve()
            .bodyToMono(OpenAiResponse.class)
            .block();
    }
}
