package kr.co.koscom.miniproject.order.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LimitSellOrderEvent extends ApplicationEvent {

    private final Long userId;
    private final Long orderId;

    public LimitSellOrderEvent(Object source, Long userId, Long orderId) {
        super(source);
        this.userId = userId;
        this.orderId = orderId;
    }
}
