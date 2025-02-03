package kr.co.koscom.miniproject.common.application.service;

import kr.co.koscom.miniproject.common.adapter.out.client.naver.clova.NaverClovaSttRequest;
import kr.co.koscom.miniproject.common.adapter.out.client.naver.clova.NaverClovaSttResponse;
import kr.co.koscom.miniproject.common.application.port.out.NaverClovaClientPort;
import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class NaverClovaApplicationService {

    private final NaverClovaClientPort<NaverClovaSttRequest, NaverClovaSttResponse> naverClovaClientPort;

    public NaverClovaSttResponse convertSpeech2Text(NaverClovaSttRequest request) {
        return naverClovaClientPort.sendRequest(request);
    }
}
