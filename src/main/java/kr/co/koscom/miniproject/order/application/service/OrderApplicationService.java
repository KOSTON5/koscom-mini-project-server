package kr.co.koscom.miniproject.order.application.service;

import java.util.Optional;
import kr.co.koscom.miniproject.common.application.dto.request.AnalyzeCommandRequest;
import kr.co.koscom.miniproject.common.application.dto.response.AnalyzeTextResponse;
import kr.co.koscom.miniproject.common.application.port.out.OpenAiClientPort;
import kr.co.koscom.miniproject.common.application.port.out.StockClientPort;
import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.common.infrastructure.exception.OpenAiChatException;
import kr.co.koscom.miniproject.order.application.dto.request.AnalyzeOrderRequest;
import kr.co.koscom.miniproject.order.application.dto.request.CancelOrderRequest;
import kr.co.koscom.miniproject.order.application.dto.request.ExecuteOrderRequest;
import kr.co.koscom.miniproject.order.application.dto.response.AnalyzeOrderResponse;
import kr.co.koscom.miniproject.order.application.dto.response.ExecuteOrderResponse;
import kr.co.koscom.miniproject.order.application.dto.response.RetrieveOrderResponse;
import kr.co.koscom.miniproject.order.application.event.LimitBuyOrderEvent;
import kr.co.koscom.miniproject.order.application.event.LimitSellOrderEvent;
import kr.co.koscom.miniproject.order.application.event.MarketBuyOrderEvent;
import kr.co.koscom.miniproject.order.application.event.MarketSellOrderEvent;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import kr.co.koscom.miniproject.order.domain.service.OrderService;
import kr.co.koscom.miniproject.order.domain.vo.CommandType;
import kr.co.koscom.miniproject.order.domain.vo.OrderCondition;
import kr.co.koscom.miniproject.order.domain.vo.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

/**
 * description : 애플리케이션의 비즈니스 흐름을 조정하는 서비스
 */
@Slf4j
@RequiredArgsConstructor
@ApplicationService
@Transactional(readOnly = true)
public class OrderApplicationService {

    private final OpenAiClientPort<AnalyzeCommandRequest, AnalyzeTextResponse> openAiClient;
    private final StockClientPort naverStockClient;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final OrderService orderService;
    private final OrderQueryService orderQueryService;

    @Transactional
    public AnalyzeOrderResponse processLLMOrder(
        AnalyzeOrderRequest analyzeOrderRequest
    ) {
        AnalyzeTextResponse analyzeTextResponse = analyzeOrderText(analyzeOrderRequest);

        analyzeTextResponse = updatePrice(analyzeTextResponse);

        Long temporalOrderId = orderService.save(createPendingOrder(analyzeTextResponse));

        return buildAnalyzeOrderResponse(analyzeTextResponse, temporalOrderId);
    }

    private AnalyzeTextResponse updatePrice(AnalyzeTextResponse analyzeTextResponse) {
        log.info("OrderApplicationService : updatePrice() : analyzeTextResponse {}",
            analyzeTextResponse);

        if (analyzeTextResponse.isPriceEmpty()) {
            return analyzeTextResponse.toBuilder()
	.price(addRealtimePrice(analyzeTextResponse.ticker()))
	.build();
        }

        return analyzeTextResponse;
    }

    public int addRealtimePrice(String ticker) {
        return naverStockClient.scrapStock(ticker).getFirstStockData().openingPrice();
    }

    private OrderEntity createPendingOrder(
        AnalyzeTextResponse analyzeTextResponse
    ) {
        return OrderEntity.builder()
            .ticker(analyzeTextResponse.ticker())
            .stockName(analyzeTextResponse.stockName())
            .orderType(CommandType.from(analyzeTextResponse.commandType()))
            .orderCondition(OrderCondition.from(analyzeTextResponse.orderCondition()))
            .orderStatus(OrderStatus.PENDING)
            .price(analyzeTextResponse.price())
            .quantity(analyzeTextResponse.quantity())
            .expirationTime(analyzeTextResponse.expirationTime())
            .build();
    }

    private AnalyzeOrderResponse buildAnalyzeOrderResponse(
        AnalyzeTextResponse analyzeTextResponse,
        Long orderId
    ) {
        return AnalyzeOrderResponse.builder()
            .orderId(orderId)
            .ticker(analyzeTextResponse.ticker())
            .stockName(analyzeTextResponse.stockName())
            .commandType(analyzeTextResponse.commandType())
            .orderCondition(analyzeTextResponse.orderCondition())
            .price(analyzeTextResponse.price())
            .quantity(analyzeTextResponse.quantity())
            .expirationTime(analyzeTextResponse.expirationTime())
            .build();
    }

    private AnalyzeTextResponse analyzeOrderText(
        AnalyzeOrderRequest analyzeOrderRequest
    ) {
        return Optional.ofNullable(analyzeOrderRequest)
            .map(AnalyzeOrderRequest::text)
            .map(text -> openAiClient.processRequest(new AnalyzeCommandRequest(text)))
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
        log.info("OrderApplicationService : executeLimitBuyOrder() : currentUserId {}", currentUserId);

        applicationEventPublisher.publishEvent(
            new LimitBuyOrderEvent(this, currentUserId, executeMarketOrderRequest.orderId())
        );

        OrderEntity order = orderQueryService.findById(executeMarketOrderRequest.orderId());
        log.info("OrderApplicationService : executeLimitBuyOrder() : order {}", order.getPrice());
        return ExecuteOrderResponse.from(order);
    }

    public ExecuteOrderResponse executeLimitSellOrder(
        Long currentUserId,
        ExecuteOrderRequest executeMarketOrderRequest
    ) {
        applicationEventPublisher.publishEvent(
            new LimitSellOrderEvent(this, currentUserId, executeMarketOrderRequest.orderId())
        );

        OrderEntity order = orderQueryService.findById(executeMarketOrderRequest.orderId());
        return ExecuteOrderResponse.from(order);
    }

    @Transactional
    public void cancelOrder(
        CancelOrderRequest cancelOrderRequest
    ) {
        orderService.cancelOrder(cancelOrderRequest.orderId());
    }

    @Transactional
    public void addToWaitingQueue(OrderEntity order) {
        orderService.changeOrderStatus(order, OrderStatus.WAITING);
        orderQueryService.update(order);
    }

    @Transactional
    public void updateOrderPrice(OrderEntity order, Integer currentMarketPrice) {
        log.info("OrderApplicationService : updateOrderPrice() : order {} currentMarketPrice {}", order, currentMarketPrice);

        OrderEntity updatedOrder = orderService.updateOrderPrice(order, currentMarketPrice);
        orderQueryService.update(updatedOrder);
    }

    public RetrieveOrderResponse retrieveOrder(Long orderId) {
        OrderEntity order = orderQueryService.findById(orderId);
        log.info("OrderApplicationService : retrieveOrder() : order {}", order.getPrice());

        return RetrieveOrderResponse.from(order);
    }
}