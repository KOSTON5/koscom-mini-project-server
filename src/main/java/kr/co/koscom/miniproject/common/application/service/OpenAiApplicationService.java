package kr.co.koscom.miniproject.common.application.service;

import java.util.Optional;
import kr.co.koscom.miniproject.common.adapter.out.client.naver.clova.NaverClovaSttRequest;
import kr.co.koscom.miniproject.common.adapter.out.client.naver.clova.NaverClovaSttResponse;
import kr.co.koscom.miniproject.common.application.dto.request.AnalyzeTextRequest;
import kr.co.koscom.miniproject.common.application.dto.response.AnalyzeTextResponse;
import kr.co.koscom.miniproject.common.application.port.out.NaverClovaClientPort;
import kr.co.koscom.miniproject.common.application.port.out.OpenAiClientPort;
import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.common.infrastructure.exception.NaverClovaSttException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class OpenAiApplicationService {

    private final OpenAiClientPort<AnalyzeTextRequest, AnalyzeTextResponse> openAiClient;
    private final NaverClovaClientPort<NaverClovaSttRequest, NaverClovaSttResponse> naverClovaClient;

    public AnalyzeTextResponse analyzeText(AnalyzeTextRequest request) {
        return openAiClient.chat(request);
    }

    private NaverClovaSttResponse sendSttRequest(
        NaverClovaSttRequest request
    ) {
        return Optional.ofNullable(naverClovaClient.sendRequest(request))
            .orElseThrow(NaverClovaSttException::new);
    }
}
