package kr.co.koscom.miniproject.user.application.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;

public record RetrieveUserOrdersResponse(List<OrderResponse> responses) {

    record OrderResponse(
        Long orderId,
        String stockName,
        String ticker,
        Integer price,
        Integer quantity,
        String orderType,
        String orderDate
    ) {

    }

    public static RetrieveUserOrdersResponse from(List<OrderEntity> orders) {
        return new RetrieveUserOrdersResponse(orders.stream().map(order -> new OrderResponse(
	order.getId(),
	order.getStockName(),
	order.getTicker(),
	order.getPrice(),
	order.getQuantity(),
	order.getOrderType().name(),
	order.getExecutionTime().toString()
            ))
            .collect(Collectors.toList()));
    }
}
