package kr.co.koscom.miniproject.application.service;

import java.math.BigDecimal;
import java.util.Optional;
import kr.co.koscom.miniproject.adapter.out.client.naver.clova.NaverClovaSttRequest;
import kr.co.koscom.miniproject.adapter.out.client.naver.clova.NaverClovaSttResponse;
import kr.co.koscom.miniproject.application.dto.request.AnalyzeTextRequest;
import kr.co.koscom.miniproject.application.dto.request.CancelOrderRequest;
import kr.co.koscom.miniproject.application.dto.request.ExecuteMarketOrderRequest;
import kr.co.koscom.miniproject.application.dto.request.ExecuteOrderRequest;
import kr.co.koscom.miniproject.application.dto.response.AnalyzeOrderResponse;
import kr.co.koscom.miniproject.application.dto.response.AnalyzeTextResponse;
import kr.co.koscom.miniproject.application.event.MarketBuyOrderEvent;
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
import kr.co.koscom.miniproject.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.infrastructure.exception.NaverClovaSttException;
import kr.co.koscom.miniproject.infrastructure.exception.OpenAiChatException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

/**
 * 애플리케이션의 비즈니스 흐름을 조정하는 서비스
 */
@RequiredArgsConstructor
@ApplicationService
@Transactional(readOnly = true)
public class OrderApplicationService {

    private final OpenAiClientPort<AnalyzeTextRequest, AnalyzeTextResponse> openAiClient;
    private final NaverClovaClientPort<NaverClovaSttRequest, NaverClovaSttResponse> naverClovaClient;

    private final ApplicationEventPublisher applicationEventPublisher;

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
            .price(analyzeTextResponse.price())
            .quantity(analyzeTextResponse.quantity())
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

    // todo : 최종 반환 결과 (예, 잔고 몇몇) 를 도출해야 할듯
    @Transactional
    public Long executeBuyOrder(
        ExecuteOrderRequest executeOrderRequest
    ) {
        OrderEntity order = orderService.findPendingOrder(executeOrderRequest.orderId());
        UserEntity user = order.getUser();
        StockEntity stock = stockService.findStockByTicker(order.getTicker());

        Integer totalPrice = stock.getCurrentPrice() * order.getQuantity();

        userService.decreaseBalance(user, totalPrice);
        return order.getId();
    }


    @Transactional
    public Long executeSellOrder(ExecuteOrderRequest executeOrderRequest) {
        OrderEntity order = orderService.findPendingOrder(executeOrderRequest.orderId());
        UserEntity user = order.getUser();
        StockEntity stock = stockService.findStockByTicker(order.getTicker());

        Integer totalPrice = stock.getCurrentPrice() * order.getQuantity();

        userService.increaseBalance(user, totalPrice);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(CancelOrderRequest cancelOrderRequest) {
        orderService.cancelOrder(cancelOrderRequest.orderId());
    }

    public void executeMarketBuyOrder(
        Long currentUserId,
        ExecuteMarketOrderRequest executeMarketOrderRequest
    ) {
        applicationEventPublisher.publishEvent(
            new MarketBuyOrderEvent(this, currentUserId, executeMarketOrderRequest.orderId())
        );

        return;
    }
}