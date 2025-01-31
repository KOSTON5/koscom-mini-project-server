package kr.co.koscom.miniproject.application.service;

import java.math.BigDecimal;
import java.util.Optional;
import kr.co.koscom.miniproject.adapter.out.client.naverclova.NaverClovaSttRequest;
import kr.co.koscom.miniproject.adapter.out.client.naverclova.NaverClovaSttResponse;
import kr.co.koscom.miniproject.application.dto.request.AnalyzeTextRequest;
import kr.co.koscom.miniproject.application.dto.request.CancelOrderRequest;
import kr.co.koscom.miniproject.application.dto.request.ExecuteOrderRequest;
import kr.co.koscom.miniproject.application.dto.response.AnalyzeOrderResponse;
import kr.co.koscom.miniproject.application.dto.response.AnalyzeTextResponse;
import kr.co.koscom.miniproject.application.port.out.NaverClovaClientPort;
import kr.co.koscom.miniproject.application.port.out.OpenAiClientPort;
import kr.co.koscom.miniproject.domain.order.entity.OrderEntity;
import kr.co.koscom.miniproject.domain.order.service.OrderService;
import kr.co.koscom.miniproject.domain.order.vo.OrderCondition;
import kr.co.koscom.miniproject.domain.order.vo.OrderStatus;
import kr.co.koscom.miniproject.domain.order.vo.OrderType;
import kr.co.koscom.miniproject.domain.stock.entity.StockEntity;
import kr.co.koscom.miniproject.domain.stock.service.StockService;
import kr.co.koscom.miniproject.domain.user.entity.UserEntity;
import kr.co.koscom.miniproject.domain.user.service.UserService;
import kr.co.koscom.miniproject.infrastructure.exception.NaverClovaSttException;
import kr.co.koscom.miniproject.infrastructure.exception.OpenAiChatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderApplicationService {

    private final OpenAiClientPort<AnalyzeTextRequest, AnalyzeTextResponse> openAiClient;
    private final NaverClovaClientPort<NaverClovaSttRequest, NaverClovaSttResponse> naverClovaClient;

    private final OrderService orderService;
    private final UserService userService;
    private final StockService stockService;

    public AnalyzeOrderResponse processLLMOrder(
        NaverClovaSttRequest naverClovaSttRequest
    ) {
        NaverClovaSttResponse naverClovaSttResponse = sendSttRequest(naverClovaSttRequest);
        AnalyzeTextResponse openAiResponse = analyzeOrderText(naverClovaSttResponse);

        Long temporalOrderId = orderService.saveOrder(createPendingOrder(openAiResponse));

        return buildAnalyzeOrderResponse(openAiResponse, temporalOrderId);
    }

    private OrderEntity createPendingOrder(
        AnalyzeTextResponse analyzeTextResponse
    ) {
        return OrderEntity.builder()
            .ticker(analyzeTextResponse.ticker())
            .orderType(OrderType.from(analyzeTextResponse.orderType()))
            .orderCondition(OrderCondition.from(analyzeTextResponse.orderCondition()))
            .orderStatus(OrderStatus.PENDING)
            .price(BigDecimal.valueOf(analyzeTextResponse.price()))
            .quantity(BigDecimal.valueOf(analyzeTextResponse.quantity()))
            .build();
    }

    private AnalyzeOrderResponse buildAnalyzeOrderResponse(
        AnalyzeTextResponse analyzeTextResponse,
        Long orderId
    ) {
        return AnalyzeOrderResponse.builder()
            .orderId(orderId)
            .ticker(analyzeTextResponse.ticker())
            .orderType(analyzeTextResponse.orderType())
            .orderCondition(analyzeTextResponse.orderCondition())
            .price(analyzeTextResponse.price())
            .quantity(analyzeTextResponse.quantity())
            .build();
    }

    private AnalyzeTextResponse analyzeOrderText(NaverClovaSttResponse response) {
        return Optional.ofNullable(response)
            .map(NaverClovaSttResponse::text)
            .map(text -> openAiClient.chat(new AnalyzeTextRequest(text)))
            .orElseThrow(OpenAiChatException::new);
    }

    private NaverClovaSttResponse sendSttRequest(NaverClovaSttRequest request) {
        return Optional.ofNullable(naverClovaClient.sendRequest(request))
            .orElseThrow(NaverClovaSttException::new);
    }

    public Long executeOrder(
        ExecuteOrderRequest executeOrderRequest
    ) {
        OrderType orderType = OrderType.from(executeOrderRequest.orderType());

        switch (orderType) {
            case BUY:
	return processBuyOrder(executeOrderRequest.orderId());
            case SELL:
	return processSellOrder(executeOrderRequest.orderId());
            default:
        }

        return null;
    }

    // todo : 최종 반환 결과 (예, 잔고 몇몇) 를 도출해야 할듯
    @Transactional
    public Long processBuyOrder(Long orderId) {
        OrderEntity order = orderService.findOrderById(orderId);
        UserEntity user = order.getUser();
        StockEntity stock = stockService.findStockByTicker(order.getTicker());

        BigDecimal totalPrice = stock.getCurrentPrice()
            .multiply(order.getQuantity());

        userService.decreaseBalance(user, totalPrice);
        return order.getId();
    }

    @Transactional
    public Long processSellOrder(Long orderId) {
        OrderEntity order = orderService.findOrderById(orderId);
        UserEntity user = order.getUser();
        StockEntity stock = stockService.findStockByTicker(order.getTicker());

        BigDecimal totalPrice = stock.getCurrentPrice()
            .multiply(order.getQuantity());

        userService.increaseBalance(user, totalPrice);
        return order.getId();
    }


    public void cancelOrder(CancelOrderRequest cancelOrderRequest) {
        orderService.deleteOrder(cancelOrderRequest.orderId());
    }
}
