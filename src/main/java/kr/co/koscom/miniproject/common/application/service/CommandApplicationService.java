package kr.co.koscom.miniproject.common.application.service;

import kr.co.koscom.miniproject.common.application.dto.request.AnalyzeCommandRequest;
import kr.co.koscom.miniproject.common.application.dto.response.AnalyzeTextResponse;
import kr.co.koscom.miniproject.common.application.port.out.OpenAiClientPort;
import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class CommandApplicationService {

    private final OpenAiClientPort<AnalyzeCommandRequest, AnalyzeTextResponse> openAiClient;

    public AnalyzeTextResponse analyze(AnalyzeCommandRequest analyzeCommandRequest) {
        log.info("CommandApplicationService: analyze() : analyzeCommandRequest {}", analyzeCommandRequest);
        AnalyzeTextResponse analyzeTextResponse = analyzeText(analyzeCommandRequest);

        return analyzeTextResponse;
    }

    private AnalyzeTextResponse analyzeText(AnalyzeCommandRequest analyzeCommandRequest) {
        return openAiClient.processRequest(analyzeCommandRequest);
    }
}
