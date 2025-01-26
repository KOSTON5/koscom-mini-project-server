package kr.co.koscom.miniproject.presentation.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.koscom.miniproject.presentation.dto.response.NaverClovaSttResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "네이버 클로바 API", description = "네이버 클로바 호출 API 명세서입니다.")
@RequestMapping("/api/clova")
public interface NaverClovaControllerDocs {

    @Operation(summary = "Naver Clova STT", description = "STT API를 호출하여 음성을 텍스트로 변환합니다.")
    @PostMapping("/stt")
    NaverClovaSttResponse speechToText();
}
