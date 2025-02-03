package kr.co.koscom.miniproject.order.adapter.in.event;

import kr.co.koscom.miniproject.order.application.event.MarketBuyOrderEvent;
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
public class LimitSellOrderListener {

    private final OrderQueryService orderQueryService;
    private final OrderExecutionService orderExecutionService;
    private final StockApplicationService stockApplicationService;

    @TransactionalEventListener
    public void handleLimitSellOrder(MarketBuyOrderEvent event) {
        log.info("Processing Limit Sell Order for Order ID: {}", event.getOrderId());

        OrderEntity order = orderQueryService.findById(event.getOrderId());
        Integer currentMarketPrice = stockApplicationService.updateMarketPrice(order.getTicker());

        if (currentMarketPrice >= order.getPrice()) {
            log.info("Market price {} meets or exceeds limit price {}. Executing order immediately.", currentMarketPrice, order.getPrice());
            orderExecutionService.executeMarketSellOrder(event.getUserId(), event.getOrderId());
        } else {
            log.info("Market price {} is below limit price {}. Adding to pending queue.", currentMarketPrice, order.getPrice());
            orderQueryService.addToPendingQueue(order);
        }
    }
}
