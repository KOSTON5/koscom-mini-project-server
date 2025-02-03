package kr.co.koscom.miniproject.common.adapter.out.client.naver.clova;

import kr.co.koscom.miniproject.common.application.port.out.NaverClovaClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class NaverClovaClient implements
    NaverClovaClientPort<NaverClovaSttRequest, NaverClovaSttResponse> {

    private final WebClient webClient;

    @Override
    public NaverClovaSttResponse sendRequest(NaverClovaSttRequest request) {
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/stt/v1/recognize";

        return webClient.post()
            .uri(apiUrl)
            .headers(headers -> {
	headers.set("Content-Type", "application/json");
	headers.set("X-NCP-APIGW-API-KEY-ID", "your-api-key-id");
	headers.set("X-NCP-APIGW-API-KEY", "your-api-key");
            })
            .bodyValue(request)
            .retrieve()
            .bodyToMono(NaverClovaSttResponse.class)
            .block();
    }
}
