package kr.co.koscom.miniproject.application.event;

import java.math.BigDecimal;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BuyOrderCreatedEvent extends ApplicationEvent {
    private final Long orderId;
    private final String ticker;
    private final int quantity;
    private final BigDecimal price;

    public BuyOrderCreatedEvent(Object source, Long orderId, String ticker, int quantity, BigDecimal price) {
        super(source);
        this.orderId = orderId;
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
    }
}
