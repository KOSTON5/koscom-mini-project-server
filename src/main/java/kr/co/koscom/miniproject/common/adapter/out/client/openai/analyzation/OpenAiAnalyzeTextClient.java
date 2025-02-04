package kr.co.koscom.miniproject.common.adapter.out.client.openai.analyzation;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import kr.co.koscom.miniproject.common.application.dto.request.AnalyzeCommandRequest;
import kr.co.koscom.miniproject.common.application.dto.response.AnalyzeTextResponse;
import kr.co.koscom.miniproject.common.application.port.out.OpenAiClientPort;
import kr.co.koscom.miniproject.common.infrastructure.exception.OpenAiChatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
@Component
public class OpenAiAnalyzeTextClient implements OpenAiClientPort<AnalyzeCommandRequest, AnalyzeTextResponse> {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${openai.chat-base-url}")
    private String OPENAI_BASE_URL;

    @Value("${openai.api-key}")
    private String API_KEY;

    private final String PROMPT = "제시되는 문장을 무조건 지정된 JSON 형식만 변환해서 응답해주세요.\n"
        + "만약에 commandType이 BALANCE, HOLDINGS인 경우에는 ticker, stockName, quantity, price, orderCondition, expirationTime은 아무 값으로 채워줘. 응답 값만 JSON 형태로 반환해야 돼\n"
        + "오늘 날짜 : " + LocalDate.now() + ". 지정가 예약 매수 계산 시 참고해주세요.\n"
        + "\n"
        + "(1) 입력 예시 : \"삼성전자 100주 시장가로 매수해줘\"\n"
        + "출력 예시 :\n"
        + "{\n"
        + "  \"commandType\": \"BUY\",\n"
        + "  \"ticker\": \"005930\",\n"
        + "  \"stockName\": \"삼성전자\",\n"
        + "  \"quantity\": 100,\n"
        + "  \"price\": null,\n"
        + "  \"orderCondition\": \"MARKET\",\n"
        + "  \"expirationTime\": \"2025-01-02\"\n"
        + "}\n"
        + "\n"
        + "(2) 입력 예시 : \"현차 10주 230000원에 예약해서 팔아줘. 예약은 다음주 까지 유효해\"\n"
        + "출력 예시 :\n"
        + "{\n"
        + "  \"commandType\": \"SELL\",\n"
        + "  \"ticker\": \"005380\",\n"
        + "  \"stockName\": \"현대자동차\",\n"
        + "  \"quantity\": 10,\n"
        + "  \"price\": 230000,\n"
        + "  \"orderCondition\": \"LIMIT\",\n"
        + "  \"expirationTime\": \"2025-02-11\"\n"
        + "}\n"
        + "\n"
        + "(3) 입력 예시 : \"현재 예수금을 알려줘\"\n"
        + "출력 예시 :\n"
        + "{\n"
        + "  \"commandType\": \"BALANCE\",\n"
        + "  \"ticker\": \"000000\",\n"
        + "  \"stockName\": \"null\",\n"
        + "  \"quantity\": 0,\n"
        + "  \"price\": 0,\n"
        + "  \"orderCondition\": \"MARKET\",\n"
        + "  \"expirationTime\": \"2025-02-04\"\n"
        + "}\n"
        + "(4) 입력 예시 : \"지금 내가 보유한 종목을 알려줘. 현재 내 주식 종목을 알려줘\"\n"
        + "출력 예시 :\n"
        + "{\n"
        + "  \"commandType\": \"HOLDINGS\",\n"
        + "  \"ticker\": \"000000\",\n"
        + "  \"stockName\": \"null\",\n"
        + "  \"quantity\": 0,\n"
        + "  \"price\": 0,\n"
        + "  \"orderCondition\": \"MARKET\",\n"
        + "  \"expirationTime\": \"2025-02-04\"\n"
        + "}\n"
        + "(5) 입력 예시 : \"현재 시세 알려줘\"\n"
        + "출력 예시 :\n"
        + "{\n"
        + "  \"commandType\": \"SEARCH\"\n"
        + "  \"ticker\": \"000000\",\n"
        + "  \"stockName\": \"주식종목\",\n"
        + "  \"quantity\": 0,\n"
        + "  \"price\": 0,\n"
        + "  \"orderCondition\": \"MARKET\",\n"
        + "  \"expirationTime\": \"2025-02-04\"\n"
        + "}\n"

        + "위와 같은 명령어를 입력받아 다음과 같은 목표 문장을 생성해주는 API를 만들어주세요.\n"
        + "조건:\n"
        + "- \"매수\"는 \"BUY\", \"매도\"는 \"SELL\", \"예수금\"은 \"BALANCE\", \"보유한 종목\"은 \"HOLDINGS\"\n"
        + "- \"시장가\"는 \"MARKET\", \"지정가\"는 \"LIMIT\"으로 변환\n"
        + "- \"삼성전자\"와 같은 회사명에 맞는 종목 티커를 \"005930\"과 같이 설정\n"
        + "- \"다시 한번 강조하자면 PURE JSON 형태로 값을 보내줘. 바로 ObjectMapper로 DTO 클래스로 변환할거야\n"
        + "명령어는 다음과 같아 : ";

    public AnalyzeTextResponse processRequest(AnalyzeCommandRequest request) {
        final String text = PROMPT + request.command();

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

        log.info("Response from OpenAI: {}", response);

        try {
            OpenAiAnalyzeResponse openAiAnalyzeResponse = objectMapper.readValue(response,
	OpenAiAnalyzeResponse.class);
            String json = openAiAnalyzeResponse.getContent();
            return objectMapper.readValue(json, AnalyzeTextResponse.class);
        } catch (Exception exception) {
            throw new OpenAiChatException();
        }
    }
}
