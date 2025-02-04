package kr.co.koscom.miniproject.order.application.service;

import java.util.Optional;
import kr.co.koscom.miniproject.common.application.dto.request.AnalyzeCommandRequest;
import kr.co.koscom.miniproject.common.application.dto.response.AnalyzeTextResponse;
import kr.co.koscom.miniproject.common.application.port.out.NaverStockClientPort;
import kr.co.koscom.miniproject.common.application.port.out.OpenAiClientPort;
import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.common.infrastructure.exception.OpenAiChatException;
import kr.co.koscom.miniproject.order.application.dto.request.AnalyzeOrderRequest;
import kr.co.koscom.miniproject.order.application.dto.request.CancelOrderRequest;
import kr.co.koscom.miniproject.order.application.dto.request.ExecuteOrderRequest;
import kr.co.koscom.miniproject.order.application.dto.response.AnalyzeOrderResponse;
import kr.co.koscom.miniproject.order.application.dto.response.ExecuteOrderResponse;
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
@Transactional
public class OrderApplicationService {

    private final OpenAiClientPort<AnalyzeCommandRequest, AnalyzeTextResponse> openAiClient;
    private final NaverStockClientPort naverStockClient;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final OrderService orderService;
    private final OrderQueryService orderQueryService;

    public AnalyzeOrderResponse processLLMOrder(
        AnalyzeOrderRequest analyzeOrderRequest
    ) {
        AnalyzeTextResponse analyzeTextResponse = analyzeOrderText(analyzeOrderRequest);
        CommandType commandType = CommandType.from(analyzeTextResponse.commandType());

        analyzeTextResponse = updatePrice(analyzeTextResponse);

        Long temporalOrderId = orderService.save(createPendingOrder(analyzeTextResponse));

        return buildAnalyzeOrderResponse(analyzeTextResponse, temporalOrderId);
    }

    private AnalyzeTextResponse updatePrice(AnalyzeTextResponse analyzeTextResponse) {
        log.info("OrderApplicationService : updatePrice() : analyzeTextResponse {}", analyzeTextResponse);

        if(analyzeTextResponse.isPriceEmpty()) {
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
            .orderType(analyzeTextResponse.commandType())
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
            .map(text -> openAiClient.chat(new AnalyzeCommandRequest(text)))
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