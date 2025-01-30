package kr.co.koscom.miniproject.adapter.in.rest;

import kr.co.koscom.miniproject.adapter.out.client.openai.OpenAiRequest;
import kr.co.koscom.miniproject.adapter.out.client.openai.OpenAiResponse;
import kr.co.koscom.miniproject.application.dto.response.AnalyzeOrderResponse;
import kr.co.koscom.miniproject.application.service.OpenAiApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OpenAiController implements OpenAiControllerDocs {

    private final OpenAiApplicationService openAiApplicationService;

    /**
     * 사용 하지 않는 API
     */
    @Override
    public ResponseEntity<OpenAiResponse> analyzeText(OpenAiRequest openAiRequest) {
        return ResponseEntity.ok(openAiApplicationService.analyzeText(openAiRequest));
    }
}
