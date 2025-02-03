package kr.co.koscom.miniproject.order.adapter.in.event;

import kr.co.koscom.miniproject.order.application.event.MarketSellOrderEvent;
import kr.co.koscom.miniproject.order.application.service.OrderExecutionService;
import kr.co.koscom.miniproject.order.application.service.OrderQueryService;
import kr.co.koscom.miniproject.stock.application.service.StockApplicationService;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import kr.co.koscom.miniproject.common.infrastructure.annotation.Listener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Listener
public class MarketSellOrderListener {

    private final OrderQueryService orderQueryService;
    private final OrderExecutionService orderExecutionService;
    private final StockApplicationService stockApplicationService;

    @TransactionalEventListener
    public void handleMarketSellOrder(MarketSellOrderEvent event) {
        log.info("BEFORE_COMMIT: Updating Order Price for Order ID: {}", event.getOrderId());

        OrderEntity order = orderQueryService.findById(event.getOrderId());
        Integer realtimeMarketPrice = stockApplicationService.updateMarketPrice(order.getTicker());

        order.updatePrice(realtimeMarketPrice);
        log.info("AFTER_COMMIT: Executing Market Sell Order for Order ID: {}", event.getOrderId());

        orderExecutionService.executeMarketSellOrder(event.getUserId(), event.getOrderId());
    }
}

