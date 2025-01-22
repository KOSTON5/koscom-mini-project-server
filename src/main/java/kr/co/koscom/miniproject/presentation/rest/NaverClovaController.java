package kr.co.koscom.miniproject.presentation.rest;

import kr.co.koscom.miniproject.presentation.dto.response.NaverClovaSttResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clova")
public class NaverClovaController {

    @PostMapping("/stt")
    public NaverClovaSttResponse speechToText() {
        return new NaverClovaSttResponse("Hello, World!");
    }
}
