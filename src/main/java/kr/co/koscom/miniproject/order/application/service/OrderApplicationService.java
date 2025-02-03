package kr.co.koscom.miniproject.order.application.service;

import java.util.Optional;
import kr.co.koscom.miniproject.common.adapter.out.client.naver.clova.NaverClovaSttRequest;
import kr.co.koscom.miniproject.common.adapter.out.client.naver.clova.NaverClovaSttResponse;
import kr.co.koscom.miniproject.common.application.dto.request.AnalyzeTextRequest;
import kr.co.koscom.miniproject.order.application.dto.request.CancelOrderRequest;
import kr.co.koscom.miniproject.order.application.dto.request.ExecuteMarketOrderRequest;
import kr.co.koscom.miniproject.order.application.dto.request.ExecuteOrderRequest;
import kr.co.koscom.miniproject.order.application.dto.response.AnalyzeOrderResponse;
import kr.co.koscom.miniproject.common.application.dto.response.AnalyzeTextResponse;
import kr.co.koscom.miniproject.order.application.dto.response.ExecuteOrderResponse;
import kr.co.koscom.miniproject.order.application.event.MarketBuyOrderEvent;
import kr.co.koscom.miniproject.order.application.event.MarketSellOrderEvent;
import kr.co.koscom.miniproject.common.application.port.out.NaverClovaClientPort;
import kr.co.koscom.miniproject.common.application.port.out.OpenAiClientPort;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import kr.co.koscom.miniproject.order.domain.service.OrderService;
import kr.co.koscom.miniproject.order.domain.vo.OrderCondition;
import kr.co.koscom.miniproject.order.domain.vo.OrderStatus;
import kr.co.koscom.miniproject.order.domain.vo.OrderType;
import kr.co.koscom.miniproject.stock.application.service.StockQueryService;
import kr.co.koscom.miniproject.stock.domain.entity.StockEntity;
import kr.co.koscom.miniproject.user.domain.entity.UserEntity;
import kr.co.koscom.miniproject.user.domain.service.UserService;
import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.common.infrastructure.exception.NaverClovaSttException;
import kr.co.koscom.miniproject.common.infrastructure.exception.OpenAiChatException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

/**
 * description : 애플리케이션의 비즈니스 흐름을 조정하는 서비스
 */
@RequiredArgsConstructor
@ApplicationService
@Transactional(readOnly = true)
public class OrderApplicationService {

    private final OpenAiClientPort<AnalyzeTextRequest, AnalyzeTextResponse> openAiClient;
    private final NaverClovaClientPort<NaverClovaSttRequest, NaverClovaSttResponse> naverClovaClient;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final OrderService orderService;
    private final OrderQueryService orderQueryService;
    private final UserService userService;
    private final StockQueryService stockQueryService;

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

    private AnalyzeTextResponse analyzeOrderText(
        NaverClovaSttResponse response
    ) {
        return Optional.ofNullable(response)
            .map(NaverClovaSttResponse::text)
            .map(text -> openAiClient.chat(new AnalyzeTextRequest(text)))
            .orElseThrow(OpenAiChatException::new);
    }

    private NaverClovaSttResponse sendSttRequest(
        NaverClovaSttRequest request
    ) {
        return Optional.ofNullable(naverClovaClient.sendRequest(request))
            .orElseThrow(NaverClovaSttException::new);
    }

    @Transactional
    public Long executeBuyOrder(
        ExecuteOrderRequest executeOrderRequest
    ) {
        OrderEntity order = orderService.findPendingOrder(executeOrderRequest.orderId());
        UserEntity user = order.getUser();
        StockEntity stock = stockQueryService.findByTicker(order.getTicker());

        Integer totalPrice = stock.getCurrentPrice() * order.getQuantity();

        userService.decreaseBalance(user, totalPrice);
        return order.getId();
    }


    @Transactional
    public Long executeSellOrder(
        ExecuteOrderRequest executeOrderRequest
    ) {
        OrderEntity order = orderService.findPendingOrder(executeOrderRequest.orderId());
        UserEntity user = order.getUser();
        StockEntity stock = stockQueryService.findByTicker(order.getTicker());

        Integer totalPrice = stock.getCurrentPrice() * order.getQuantity();

        userService.increaseBalance(user, totalPrice);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(
        CancelOrderRequest cancelOrderRequest
    ) {
        orderService.cancelOrder(cancelOrderRequest.orderId());
    }

    @Transactional
    public ExecuteOrderResponse executeMarketBuyOrder(
        Long currentUserId,
        ExecuteMarketOrderRequest executeMarketOrderRequest
    ) {
        applicationEventPublisher.publishEvent(
            new MarketBuyOrderEvent(this, currentUserId, executeMarketOrderRequest.orderId())
        );

        OrderEntity order = orderQueryService.findById(executeMarketOrderRequest.orderId());
        return ExecuteOrderResponse.from(order);
    }

    @Transactional
    public ExecuteOrderResponse executeMarketSellOrder(
        Long currentUserId,
        ExecuteMarketOrderRequest executeMarketOrderRequest
    ) {
        applicationEventPublisher.publishEvent(
            new MarketSellOrderEvent(this, currentUserId, executeMarketOrderRequest.orderId())
        );

        OrderEntity order = orderQueryService.findById(executeMarketOrderRequest.orderId());
        return ExecuteOrderResponse.from(order);
    }

    @Transactional
    public ExecuteOrderResponse executeLimitBuyOrder(
        Long currentUserId,
        ExecuteMarketOrderRequest executeMarketOrderRequest
    ) {
        applicationEventPublisher.publishEvent(
            new MarketSellOrderEvent(this, currentUserId, executeMarketOrderRequest.orderId())
        );

        OrderEntity order = orderQueryService.findById(executeMarketOrderRequest.orderId());
        return ExecuteOrderResponse.from(order);
    }

    @Transactional
    public ExecuteOrderResponse executeLimitSellOrder(
        Long currentUserId,
        ExecuteMarketOrderRequest executeMarketOrderRequest
    ) {
        applicationEventPublisher.publishEvent(
            new MarketSellOrderEvent(this, currentUserId, executeMarketOrderRequest.orderId())
        );

        OrderEntity order = orderQueryService.findById(executeMarketOrderRequest.orderId());
        return ExecuteOrderResponse.from(order);
    }
}