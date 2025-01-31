package kr.co.koscom.miniproject.adapter.in.rest;

import kr.co.koscom.miniproject.application.dto.request.AnalyzeTextRequest;
import kr.co.koscom.miniproject.application.dto.response.AnalyzeTextResponse;
import kr.co.koscom.miniproject.application.service.OpenAiApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OpenAiController implements OpenAiControllerDocs {

    private final OpenAiApplicationService openAiApplicationService;

    @Override
    public ResponseEntity<AnalyzeTextResponse> analyzeText(AnalyzeTextRequest request) {
        return ResponseEntity.ok(openAiApplicationService.analyzeText(request));
    }
}
