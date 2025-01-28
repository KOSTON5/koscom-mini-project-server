package kr.co.koscom.miniproject.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.koscom.miniproject.adapter.out.client.openai.OpenAiRequest;
import kr.co.koscom.miniproject.adapter.out.client.openai.OpenAiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Tag(name = "Open Ai API", description = "Open API 기반의 LLM을 사용하는 API 명세서입니다.")
@RequestMapping("/api/openai")
public interface OpenAiControllerDocs {
    @Operation(summary = "Analyze Text", description = "자연어로 된 텍스트를 분석합니다.")
    @PostMapping("/analyze")
    ResponseEntity<OpenAiResponse> analyzeText (
        @RequestPart OpenAiRequest openAiRequest
    );

}
