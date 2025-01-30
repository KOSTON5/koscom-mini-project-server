package kr.co.koscom.miniproject.application.service;

import java.math.BigDecimal;
import kr.co.koscom.miniproject.adapter.out.client.naverclova.NaverClovaSttRequest;
import kr.co.koscom.miniproject.adapter.out.client.naverclova.NaverClovaSttResponse;
import kr.co.koscom.miniproject.adapter.out.client.openai.OpenAiRequest;
import kr.co.koscom.miniproject.adapter.out.client.openai.OpenAiResponse;
import kr.co.koscom.miniproject.application.dto.request.CancelOrderRequest;
import kr.co.koscom.miniproject.application.dto.request.ExecuteOrderRequest;
import kr.co.koscom.miniproject.application.dto.response.AnalyzeOrderResponse;
import kr.co.koscom.miniproject.application.port.out.NaverClovaClientPort;
import kr.co.koscom.miniproject.application.port.out.OpenAiClientPort;
import kr.co.koscom.miniproject.domain.order.entity.OrderEntity;
import kr.co.koscom.miniproject.domain.order.service.OrderService;
import kr.co.koscom.miniproject.domain.order.vo.OrderCondition;
import kr.co.koscom.miniproject.domain.order.vo.OrderType;
import kr.co.koscom.miniproject.infrastructure.exception.NaverClovaSttException;
import kr.co.koscom.miniproject.infrastructure.exception.OpenAiChatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderApplicationService {

    private final OrderService orderService;
    private final OpenAiClientPort<OpenAiRequest, OpenAiResponse> openAiClient;
    private final NaverClovaClientPort<NaverClovaSttRequest, NaverClovaSttResponse> naverClovaClient;

    public AnalyzeOrderResponse createOrderByLLM(NaverClovaSttRequest naverClovaSttRequest) {
        NaverClovaSttResponse naverClovaSttResponse = sendSttRequest(naverClovaSttRequest);
        OpenAiResponse openAiResponse = analyzeOrderText(naverClovaSttResponse);

        Long temporalOrderId = orderService.createTemporalOrder(createOrderEntity(openAiResponse));

        return buildAnalyzeOrderResponse(openAiResponse, temporalOrderId);
    }

    private OrderEntity createOrderEntity(OpenAiResponse aiResponse) {
        return OrderEntity.builder()
            .ticker(aiResponse.ticker())
            .orderType(OrderType.from(aiResponse.orderType()))
            .orderCondition(OrderCondition.from(aiResponse.orderCondition()))
            .price(BigDecimal.valueOf(aiResponse.price()))
            .quantity(BigDecimal.valueOf(aiResponse.quantity()))
            .expirationTime(aiResponse.expirationTime())
            .build();
    }

    private AnalyzeOrderResponse buildAnalyzeOrderResponse(
        OpenAiResponse aiResponse,
        Long orderId
    ) {

        return AnalyzeOrderResponse.builder()
            .orderId(orderId)
            .ticker(aiResponse.ticker())
            .orderType(aiResponse.orderType())
            .orderCondition(aiResponse.orderCondition())
            .price(aiResponse.price())
            .quantity(aiResponse.quantity())
            .expirationTime(aiResponse.expirationTime())
            .build();
    }

    private OpenAiResponse analyzeOrderText(NaverClovaSttResponse naverClovaSttResponse) {
        if (naverClovaSttResponse == null || naverClovaSttResponse.text() == null) {
            throw new NaverClovaSttException();
        }

        OpenAiRequest openAiRequest = OpenAiRequest.builder()
            .text(naverClovaSttResponse.text())
            .build();

        OpenAiResponse openAiResponse = openAiClient.chat(openAiRequest);
        if (openAiResponse == null) {
            throw new OpenAiChatException();
        }

        return openAiResponse;
    }

    private NaverClovaSttResponse sendSttRequest(NaverClovaSttRequest request) {
        NaverClovaSttResponse response = naverClovaClient.sendRequest(request);
        if (response == null) {
            throw new IllegalStateException("STT 변환 요청 실패");
        }
        return response;
    }


    public Long executeOrder(ExecuteOrderRequest executeOrderRequest) {
        OrderType orderType = OrderType.from(executeOrderRequest.orderType());

        switch (orderType) {
            case BUY:
	// todo : 처리할 예정
	break;
            case SELL:
	// todo : 처리할 예정
	break;
            default:
        }

        return null;
    }

    public void cancelOrder(CancelOrderRequest cancelOrderRequest) {
        orderService.deleteOrder(cancelOrderRequest.orderId());
    }
}
