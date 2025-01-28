package kr.co.koscom.miniproject.adapter.in.rest;

import static kr.co.koscom.miniproject.infrastructure.utils.FileValidator.isNotAudioType;
import static kr.co.koscom.miniproject.infrastructure.utils.FileValidator.isSizeOver;

import kr.co.koscom.miniproject.adapter.out.client.naverclova.NaverClovaSttRequest;
import kr.co.koscom.miniproject.adapter.out.client.naverclova.NaverClovaSttResponse;
import kr.co.koscom.miniproject.application.service.NaverClovaApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NaverClovaController implements NaverClovaControllerDocs {

    private final NaverClovaApplicationService naverClovaApplicationService;

    @Override
    public ResponseEntity<NaverClovaSttResponse> convertSpeech2Text(NaverClovaSttRequest request) {
        if (isNotAudioType(request.audio()) || isSizeOver(request.audio())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(naverClovaApplicationService.convertSpeech2Text(request));
    }
}
