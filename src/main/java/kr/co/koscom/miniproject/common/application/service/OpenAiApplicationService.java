package kr.co.koscom.miniproject.common.application.service;

import kr.co.koscom.miniproject.common.application.dto.request.AnalyzeCommandRequest;
import kr.co.koscom.miniproject.common.application.dto.request.SpeechToText64Request;
import kr.co.koscom.miniproject.common.application.dto.response.AnalyzeTextResponse;
import kr.co.koscom.miniproject.common.application.dto.response.SpeechToTextResponse;
import kr.co.koscom.miniproject.common.application.port.out.OpenAiClientPort;
import kr.co.koscom.miniproject.common.application.port.out.TranscriptionClientPort;
import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class OpenAiApplicationService {

    private final OpenAiClientPort<AnalyzeCommandRequest, AnalyzeTextResponse> openAiAnalyzeClient;
    private final TranscriptionClientPort<MultipartFile, String> transcriptionClientPort;

    public AnalyzeTextResponse analyzeText(final AnalyzeCommandRequest analyzeCommandRequest) {
        return openAiAnalyzeClient.processRequest(analyzeCommandRequest);
    }

    public SpeechToTextResponse speechToText(final MultipartFile audioFile) {
        log.info("OpenAiApplicationService : speechToText() : audioFile.getOriginalFilename {}",
            audioFile.getOriginalFilename());

        return new SpeechToTextResponse(
            transcriptionClientPort.processRequest(audioFile));
    }
}
