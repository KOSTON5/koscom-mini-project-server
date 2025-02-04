package kr.co.koscom.miniproject.common.adapter.in.rest;

import kr.co.koscom.miniproject.common.application.dto.request.AnalyzeCommandRequest;
import kr.co.koscom.miniproject.common.application.dto.request.SpeechToTextRequest;
import kr.co.koscom.miniproject.common.application.dto.response.AnalyzeTextResponse;
import kr.co.koscom.miniproject.common.application.dto.response.SpeechToTextResponse;
import kr.co.koscom.miniproject.common.application.service.OpenAiApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OpenAiController implements OpenAiControllerDocs {

    private final OpenAiApplicationService openAiApplicationService;

    @Override
    public ResponseEntity<AnalyzeTextResponse> analyzeText(AnalyzeCommandRequest request) {
        log.info("OpenAiController : analyzeText()");
        return ResponseEntity.ok(openAiApplicationService.analyzeText(request));
    }

    @Override
    public ResponseEntity<SpeechToTextResponse> speechToText(MultipartFile audioFile) {
        log.info("OpenAiController : speechToText() : audioFile {}", audioFile.getOriginalFilename());
        return ResponseEntity.ok(openAiApplicationService.speechToText(audioFile));
    }
}
