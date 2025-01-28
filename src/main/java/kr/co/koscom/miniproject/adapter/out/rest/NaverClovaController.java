package kr.co.koscom.miniproject.adapter.out.rest;

import kr.co.koscom.miniproject.infrastructure.utils.FileValidator;
import kr.co.koscom.miniproject.adapter.out.client.naverclova.NaverClovaSttRequest;
import kr.co.koscom.miniproject.adapter.out.client.naverclova.NaverClovaSttResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NaverClovaController implements NaverClovaControllerDocs {

    private final FileValidator fileValidator;

    @Override
    public ResponseEntity<NaverClovaSttResponse> speechToText(NaverClovaSttRequest request) {
        if (fileValidator.isAudio(request.audio()) && fileValidator.isSizeNotOver(
            request.audio())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new NaverClovaSttResponse(""));
    }
}
