package kr.co.koscom.miniproject.adapter.in.event;

import kr.co.koscom.miniproject.application.event.MarketBuyOrderEvent;
import kr.co.koscom.miniproject.application.service.OrderExecutionService;
import kr.co.koscom.miniproject.application.service.OrderQueryService;
import kr.co.koscom.miniproject.application.service.StockApplicationService;
import kr.co.koscom.miniproject.domain.order.entity.OrderEntity;
import kr.co.koscom.miniproject.infrastructure.annotation.Listener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Listener
public class MarketOrderBuyListener {

    private final OrderQueryService orderQueryService;
    private final OrderExecutionService orderExecutionService;
    private final StockApplicationService stockApplicationService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleBeforeCommit(MarketBuyOrderEvent event) {
        log.info("BEFORE_COMMIT: Updating Order Price for Order ID: {}", event.getOrderId());

        OrderEntity order = orderQueryService.findById(event.getOrderId());
        Integer realtimeMarketPrice = stockApplicationService.updateMarketPrice(order.getTicker());

        order.updatePrice(realtimeMarketPrice);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAfterCommit(MarketBuyOrderEvent event) {
        log.info("AFTER_COMMIT: Executing Market Buy Order for Order ID: {}", event.getOrderId());
        orderExecutionService.executeMarketBuyOrder(event.getUserId(), event.getOrderId());
    }
}
