package kr.co.koscom.miniproject.order.application.service;

import java.util.Optional;
import kr.co.koscom.miniproject.common.adapter.out.client.naver.clova.NaverClovaSttRequest;
import kr.co.koscom.miniproject.common.adapter.out.client.naver.clova.NaverClovaSttResponse;
import kr.co.koscom.miniproject.common.application.dto.request.AnalyzeTextRequest;
import kr.co.koscom.miniproject.order.application.dto.request.AnalyzeOrderRequest;
import kr.co.koscom.miniproject.order.application.dto.request.CancelOrderRequest;
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
import kr.co.koscom.miniproject.user.domain.service.UserService;
import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.common.infrastructure.exception.OpenAiChatException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

/**
 * description : 애플리케이션의 비즈니스 흐름을 조정하는 서비스
 */
@RequiredArgsConstructor
@ApplicationService
@Transactional
public class OrderApplicationService {

    private final OpenAiClientPort<AnalyzeTextRequest, AnalyzeTextResponse> openAiClient;
    private final NaverClovaClientPort<NaverClovaSttRequest, NaverClovaSttResponse> naverClovaClient;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final OrderService orderService;
    private final OrderQueryService orderQueryService;
    private final UserService userService;
    private final StockQueryService stockQueryService;

    public AnalyzeOrderResponse processLLMOrder(
        AnalyzeOrderRequest analyzeOrderRequest
    ) {
        AnalyzeTextResponse openAiResponse = analyzeOrderText(analyzeOrderRequest);

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
        AnalyzeOrderRequest analyzeOrderRequest
    ) {
        return Optional.ofNullable(analyzeOrderRequest)
            .map(AnalyzeOrderRequest::text)
            .map(text -> openAiClient.chat(new AnalyzeTextRequest(text)))
            .orElseThrow(OpenAiChatException::new);
    }

    public ExecuteOrderResponse executeMarketBuyOrder(
        Long currentUserId,
        ExecuteOrderRequest executeMarketOrderRequest
    ) {
        applicationEventPublisher.publishEvent(
            new MarketBuyOrderEvent(this, currentUserId, executeMarketOrderRequest.orderId())
        );

        OrderEntity order = orderQueryService.findById(executeMarketOrderRequest.orderId());
        return ExecuteOrderResponse.from(order);
    }

    public ExecuteOrderResponse executeMarketSellOrder(
        Long currentUserId,
        ExecuteOrderRequest executeMarketOrderRequest
    ) {
        applicationEventPublisher.publishEvent(
            new MarketSellOrderEvent(this, currentUserId, executeMarketOrderRequest.orderId())
        );

        OrderEntity order = orderQueryService.findById(executeMarketOrderRequest.orderId());
        return ExecuteOrderResponse.from(order);
    }

    public ExecuteOrderResponse executeLimitBuyOrder(
        Long currentUserId,
        ExecuteOrderRequest executeMarketOrderRequest
    ) {
        applicationEventPublisher.publishEvent(
            new MarketSellOrderEvent(this, currentUserId, executeMarketOrderRequest.orderId())
        );

        OrderEntity order = orderQueryService.findById(executeMarketOrderRequest.orderId());
        return ExecuteOrderResponse.from(order);
    }

    public ExecuteOrderResponse executeLimitSellOrder(
        Long currentUserId,
        ExecuteOrderRequest executeMarketOrderRequest
    ) {
        applicationEventPublisher.publishEvent(
            new MarketSellOrderEvent(this, currentUserId, executeMarketOrderRequest.orderId())
        );

        OrderEntity order = orderQueryService.findById(executeMarketOrderRequest.orderId());
        return ExecuteOrderResponse.from(order);
    }

    public void cancelOrder(
        CancelOrderRequest cancelOrderRequest
    ) {
        orderService.cancelOrder(cancelOrderRequest.orderId());
    }
}

//    public Long executeBuyOrder(
//        ExecuteOrderRequest executeOrderRequest
//    ) {
//        OrderEntity order = orderService.findPendingOrder(executeOrderRequest.orderId());
//        UserEntity user = order.getUser();
//        StockEntity stock = stockQueryService.findByTicker(order.getTicker());
//
//        Integer totalPrice = stock.getCurrentPrice() * order.getQuantity();
//
//        userService.decreaseBalance(user, totalPrice);
//        return order.getId();
//    }
//
//
//    public Long executeSellOrder(
//        ExecuteOrderRequest executeOrderRequest
//    ) {
//        OrderEntity order = orderService.findPendingOrder(executeOrderRequest.orderId());
//        UserEntity user = order.getUser();
//        StockEntity stock = stockQueryService.findByTicker(order.getTicker());
//
//        Integer totalPrice = stock.getCurrentPrice() * order.getQuantity();
//
//        userService.increaseBalance(user, totalPrice);
//        return order.getId();
//    }
