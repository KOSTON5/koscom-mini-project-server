package kr.co.koscom.miniproject.common.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.koscom.miniproject.common.application.dto.request.AnalyzeCommandRequest;
import kr.co.koscom.miniproject.common.application.dto.request.SpeechToTextRequest;
import kr.co.koscom.miniproject.common.application.dto.response.AnalyzeTextResponse;
import kr.co.koscom.miniproject.common.application.dto.response.SpeechToTextResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Open Ai API", description = "Open API 기반의 LLM을 사용하는 API 명세서입니다.")
@RequestMapping("/api/openai")
public interface OpenAiControllerDocs {

    @Operation(summary = "Analyze Text", description = "LLM 서버가 제대로 동작하는지 확인하기 위한 API 입니다.")
    @PostMapping("/analyze")
    ResponseEntity<AnalyzeTextResponse> analyzeText(
        @RequestBody AnalyzeCommandRequest analyzeTextRequest
    );

    @Operation(summary = "Speech To Text", description = "음성을 텍스트로 변환하는 API 입니다.")
    @PostMapping("/stt")
    ResponseEntity<SpeechToTextResponse> speechToText(
        @RequestPart("audioFile")MultipartFile audioFile
    );

}
