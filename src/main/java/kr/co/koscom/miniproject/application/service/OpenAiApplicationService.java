package kr.co.koscom.miniproject.application.service;

import java.math.BigDecimal;
import kr.co.koscom.miniproject.adapter.out.client.openai.OpenAiRequest;
import kr.co.koscom.miniproject.adapter.out.client.openai.OpenAiResponse;
import kr.co.koscom.miniproject.application.dto.response.AnalyzeOrderResponse;
import kr.co.koscom.miniproject.application.port.out.OpenAiClientPort;
import kr.co.koscom.miniproject.domain.order.entity.OrderEntity;
import kr.co.koscom.miniproject.domain.order.service.OrderService;
import kr.co.koscom.miniproject.domain.order.vo.OrderCondition;
import kr.co.koscom.miniproject.domain.order.vo.OrderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OpenAiApplicationService {

    private final OpenAiClientPort<OpenAiRequest, OpenAiResponse> openAiClient;
    private final OrderService orderService;

    public OpenAiResponse analyzeText(OpenAiRequest openAiRequest) {
        OpenAiResponse response = openAiClient.chat(openAiRequest);

        Long temporalOrderId = orderService.createTemporalOrder(
            OrderEntity.builder()
	.ticker(response.ticker())
	.orderType(OrderType.from(response.orderType()))
	.orderCondition(OrderCondition.from(response.orderCondition()))
	.price(BigDecimal.valueOf(response.price()))
	.quantity(BigDecimal.valueOf(response.quantity()))
	.expirationTime(response.expirationTime())
	.build()
        );

        return response;
    }
}
