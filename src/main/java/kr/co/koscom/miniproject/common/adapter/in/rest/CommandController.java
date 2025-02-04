package kr.co.koscom.miniproject.common.adapter.in.rest;

import kr.co.koscom.miniproject.common.application.dto.request.AnalyzeCommandRequest;
import kr.co.koscom.miniproject.common.application.dto.response.AnalyzeCommandResponse;
import kr.co.koscom.miniproject.common.application.service.CommandApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommandController implements CommandControllerDocs {

    private final CommandApplicationService commandApplicationService;

    @Override
    public ResponseEntity<AnalyzeCommandResponse> analyzeCommand(AnalyzeCommandRequest analyzeCommandRequest) {
        return ResponseEntity.ok(commandApplicationService.analyze(analyzeCommandRequest));
    }
}
