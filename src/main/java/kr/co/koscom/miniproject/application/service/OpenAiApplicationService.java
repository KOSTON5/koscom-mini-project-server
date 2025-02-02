package kr.co.koscom.miniproject.application.service;

import kr.co.koscom.miniproject.application.dto.request.AnalyzeTextRequest;
import kr.co.koscom.miniproject.application.dto.response.AnalyzeTextResponse;
import kr.co.koscom.miniproject.application.port.out.OpenAiClientPort;
import kr.co.koscom.miniproject.infrastructure.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class OpenAiApplicationService {

    private final OpenAiClientPort<AnalyzeTextRequest, AnalyzeTextResponse> openAiClient;

    public AnalyzeTextResponse analyzeText(AnalyzeTextRequest request) {
        return openAiClient.chat(request);
    }
}
