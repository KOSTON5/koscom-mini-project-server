package kr.co.koscom.miniproject.order.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MarketSellOrderEvent extends ApplicationEvent {

    private final Long userId;
    private final Long orderId;

    public MarketSellOrderEvent(Object source, Long userId, Long orderId) {
        super(source);
        this.userId = userId;
        this.orderId = orderId;
    }
}
