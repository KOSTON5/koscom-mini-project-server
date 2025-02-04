package kr.co.koscom.miniproject.common.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.koscom.miniproject.common.application.dto.request.AnalyzeCommandRequest;
import kr.co.koscom.miniproject.common.application.dto.response.AnalyzeTextResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "명령 API", description = "사용자의 음성 명령을 처리하는 API 명세서입니다.")
@RequestMapping("/api/command")
public interface CommandControllerDocs {

    @Operation(summary = "음성 명령을 처리", description = "사용자의 음성 명령을 처리합니다.")
    @PostMapping("/analyze")
    ResponseEntity<AnalyzeTextResponse> analyzeCommand(
        @RequestBody AnalyzeCommandRequest analyzeCommandRequest
    );
}
