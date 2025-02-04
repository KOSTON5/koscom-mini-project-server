package kr.co.koscom.miniproject.order.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import kr.co.koscom.miniproject.order.domain.vo.CommandType;
import kr.co.koscom.miniproject.order.domain.vo.OrderCondition;
import kr.co.koscom.miniproject.order.domain.vo.OrderStatus;

public record RetrieveOrderResponse(
    Long orderId, String ticker,
    CommandType orderType, OrderCondition orderCondition, OrderStatus orderStatus,
    Integer executedPrice, Integer executedQuantity, Integer totalAmount,
    LocalDateTime executionTime, LocalDate expirationTime
) {

    public static RetrieveOrderResponse from(OrderEntity order) {
        return new RetrieveOrderResponse(
            order.getId(),
            order.getTicker(),
            order.getOrderType(),
            order.getOrderCondition(),
            order.getOrderStatus(),
            order.getPrice(),
            order.getQuantity(),
            order.getPrice() * order.getQuantity(),
            order.getExecutionTime(),
            order.getExpirationTime()
        );
    }
}
