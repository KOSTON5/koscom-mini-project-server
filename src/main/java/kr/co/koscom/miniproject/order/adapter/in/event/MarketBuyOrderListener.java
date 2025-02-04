package kr.co.koscom.miniproject.order.adapter.in.event;

import kr.co.koscom.miniproject.order.application.event.MarketBuyOrderEvent;
import kr.co.koscom.miniproject.order.application.service.OrderExecutionService;
import kr.co.koscom.miniproject.order.application.service.OrderQueryService;
import kr.co.koscom.miniproject.order.domain.vo.OrderStatus;
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
public class MarketBuyOrderListener {

    private final OrderQueryService orderQueryService;
    private final OrderExecutionService orderExecutionService;
    private final StockApplicationService stockApplicationService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleMarketBuyOrder(MarketBuyOrderEvent event) {
        log.info("MarketBuyOrderListener : handleMarketBuyOrder start()", event.getOrderId());
        OrderEntity order = orderQueryService.findById(event.getOrderId());

        if (isAlreadyExecuted(order)) {
            return;
        }

        Integer realtimeMarketPrice = stockApplicationService.retrieveRealtimeMarketPrice(order.getTicker());
        order.updatePrice(realtimeMarketPrice);
        orderExecutionService.executeBuyOrder(event.getUserId(), event.getOrderId());
        log.info("MarketBuyOrderListener : Executing Market Buy Order for Order ID: {}, realtimePrice: {}", event.getOrderId(), realtimeMarketPrice);
    }

    private static boolean isAlreadyExecuted(OrderEntity order) {
        if (order.getOrderStatus() == OrderStatus.MATCHED) {
            return true;
        }

        return false;
    }
}
