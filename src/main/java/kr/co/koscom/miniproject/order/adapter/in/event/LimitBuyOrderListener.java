package kr.co.koscom.miniproject.order.adapter.in.event;

import kr.co.koscom.miniproject.order.application.event.LimitBuyOrderEvent;
import kr.co.koscom.miniproject.order.application.service.OrderApplicationService;
import kr.co.koscom.miniproject.order.application.service.OrderExecutionService;
import kr.co.koscom.miniproject.order.application.service.OrderQueryService;
import kr.co.koscom.miniproject.stock.application.service.StockApplicationService;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import kr.co.koscom.miniproject.common.infrastructure.annotation.Listener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Listener
public class LimitBuyOrderListener {

    private final OrderQueryService orderQueryService;
    private final OrderExecutionService orderExecutionService;
    private final OrderApplicationService orderApplicationService;
    private final StockApplicationService stockApplicationService;

    @TransactionalEventListener
    public void handleLimitBuyOrder(LimitBuyOrderEvent event) {
        log.info("LimitBuyOrderListener : handleLimitBuyOrder() Start: {}", event.getOrderId());

        OrderEntity order = orderQueryService.findById(event.getOrderId());
        Integer realtimeMarketPrice = stockApplicationService.retrieveRealtimeMarketPrice(
            order.getTicker());

        if (realtimeMarketPrice < order.getPrice()) {
            order.updatePrice(realtimeMarketPrice);
            orderApplicationService.updateOrderPrice(order, realtimeMarketPrice);
            orderExecutionService.executeBuyOrder(event.getUserId(), event.getOrderId());
        } else {
            orderApplicationService.addToWaitingQueue(order);
        }
    }
}
