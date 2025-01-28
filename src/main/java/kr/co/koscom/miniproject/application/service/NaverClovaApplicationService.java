package kr.co.koscom.miniproject.application.service;

import kr.co.koscom.miniproject.adapter.out.client.naverclova.NaverClovaSttRequest;
import kr.co.koscom.miniproject.adapter.out.client.naverclova.NaverClovaSttResponse;
import kr.co.koscom.miniproject.application.port.out.NaverClovaClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NaverClovaApplicationService {

    private final NaverClovaClientPort<NaverClovaSttRequest, NaverClovaSttResponse> naverClovaClientPort;

    public NaverClovaSttResponse convertSpeech2Text(NaverClovaSttRequest request) {

        return naverClovaClientPort.sendRequest(request);
    }
}
