package kr.co.koscom.miniproject.adapter.in.rest;

import kr.co.koscom.miniproject.adapter.out.client.openai.OpenAiRequest;
import kr.co.koscom.miniproject.adapter.out.client.openai.OpenAiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OpenAiController implements OpenAiControllerDocs {

    @Override
    public ResponseEntity<OpenAiResponse> analyzeText(OpenAiRequest openAiRequest) {
        return null;
    }
}
