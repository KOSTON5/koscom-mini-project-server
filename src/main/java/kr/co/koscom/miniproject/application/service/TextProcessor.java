package kr.co.koscom.miniproject.application.service;

import kr.co.koscom.miniproject.application.port.out.NaverClovaClientPort;
import kr.co.koscom.miniproject.application.port.out.OpenAiClientPort;
import kr.co.koscom.miniproject.adapter.out.client.naverclova.NaverClovaSttRequest;
import kr.co.koscom.miniproject.adapter.out.client.naverclova.NaverClovaSttResponse;
import kr.co.koscom.miniproject.adapter.out.client.openai.OpenAiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TextProcessor {

    private final NaverClovaClientPort<NaverClovaSttRequest, NaverClovaSttResponse> naverClovaClientPort;
    private final OpenAiClientPort<OpenAiRequest, Object> openAiClientPort;
    public String processSpeechToText(NaverClovaSttRequest request) {
        NaverClovaSttResponse response = naverClovaClientPort.sendRequest(request);

        // todo : GPT로 부터 받은 응답 처리
        Object chat = openAiClientPort.chat(new OpenAiRequest(response.text()));

        // todo : 분기 처리 로직 추가
        return "";
    }
}
