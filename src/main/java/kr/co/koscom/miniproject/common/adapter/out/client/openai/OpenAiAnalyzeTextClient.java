package kr.co.koscom.miniproject.common.adapter.out.client.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.koscom.miniproject.common.application.dto.request.AnalyzeTextRequest;
import kr.co.koscom.miniproject.common.application.dto.response.AnalyzeTextResponse;
import kr.co.koscom.miniproject.common.application.port.out.OpenAiClientPort;
import kr.co.koscom.miniproject.common.infrastructure.exception.OpenAiChatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
@Component
public class OpenAiAnalyzeTextClient implements OpenAiClientPort<AnalyzeTextRequest, AnalyzeTextResponse> {

    private final WebClient webClient;
    private final String OPENAI_BASE_URL = "https://api.openai.com/v1/chat/completions";
    private final String API_KEY = "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";

    /**
     * Json (포맷) Structure를 사용해서 리팩토링 하자. COT Programming
     */
    private final String PROMPT = "제시되는 문장을 JSON 형식만 변환해서 응답해주세요. 다른 응답 값은 사절입니다.\n"
        + "\n"
        + "예제 문장은 다음과 같아요: \"삼성전자 100주 50000원에 시장가로 매수해줘\"\n"
        + "\n"
        + "출력 예시:\n"
        + "{\n"
        + "  \"orderType\": \"BUY\",\n"
        + "  \"ticker\": \"005930\",\n"
        + "  \"stockName\": \"삼성전자\",\n"
        + "  \"quantity\": 100,\n"
        + "  \"price\": 50000,\n"
        + "  \"orderCondition\": \"MARKET\",\n"
        + "}\n"
        + "\n"
        + "조건:\n"
        + "- \"매수\"는 \"BUY\", \"매도\"는 \"SELL\"로 변환\n"
        + "- \"시장가\"는 \"MARKET\", \"지정가\"는 \"LIMIT\"으로 변환\n"
        + "- \"삼성전자\"와 같은 회사명에 맞는 종목 티커를 \"005930\"과 같이 설정\n"
        + "- \"만약 시장가에 대한 값이 null이라면 현재 해당 종목에 대한 값을 조회해서 넣어줘\n"
        + "목표 문장은 다음과 같아: ";

    public AnalyzeTextResponse chat(AnalyzeTextRequest request) {

        final String text = PROMPT + request.text();

        String response = webClient.post()
            .uri(OPENAI_BASE_URL)
            .headers(headers -> {
	headers.set("Content-Type", "application/json");
	headers.set("Authorization", "Bearer " + API_KEY);
            })
            .bodyValue(OpenAiAnalyzeTextRequest.from(text))
            .retrieve()
            .bodyToMono(String.class)
            .block();

        log.info("OpenAiAnalyzeTextClient response : {}", response);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OpenAiAnalyzeResponse openAiResponse = objectMapper.readValue(response, OpenAiAnalyzeResponse.class);

            String jsonContent = openAiResponse.getContent();
            log.info("Parsed JSON Content: {}", jsonContent);

            return objectMapper.readValue(jsonContent, AnalyzeTextResponse.class);
        } catch (Exception e) {
            log.error("Error parsing OpenAI response", e);
            throw new OpenAiChatException();
        }
    }
}
