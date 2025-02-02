package kr.co.koscom.miniproject.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.koscom.miniproject.adapter.out.client.naver.clova.NaverClovaSttRequest;
import kr.co.koscom.miniproject.adapter.out.client.naver.clova.NaverClovaSttResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Tag(name = "네이버 클로바 API", description = "네이버 클로바를 호출하는 API 명세서입니다.")
@RequestMapping("/api/clova")
public interface NaverClovaControllerDocs {

    @Operation(summary = "Speech To Text", description = "STT 서비스를 개별적으로 테스트할 때 사용하는 API 입니다.")
    @PostMapping("/stt")
    ResponseEntity<NaverClovaSttResponse> convertSpeech2Text(
        @RequestPart NaverClovaSttRequest request
    );
}
