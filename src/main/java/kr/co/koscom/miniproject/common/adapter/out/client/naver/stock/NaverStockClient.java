package kr.co.koscom.miniproject.common.adapter.out.client.naver.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.koscom.miniproject.common.application.port.out.NaverStockClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class NaverStockClient implements NaverStockClientPort {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private static final String NAVER_FINANCE_URL = "https://polling.finance.naver.com/api/realtime?query=SERVICE_ITEM:";

    public NaverStockResponse scrapStock(String ticker) {
        return webClient.get()
            .uri(NAVER_FINANCE_URL + ticker)
            .retrieve()
            .bodyToMono(String.class)
            .map(response -> parseResponse(response))
            .block();
    }

    private NaverStockResponse parseResponse(String response) {
        try {
            return objectMapper.readValue(response, NaverStockResponse.class);
        } catch (Exception exception) {
            throw new RuntimeException("JSON Parsing Error", exception);
        }
    }
}
