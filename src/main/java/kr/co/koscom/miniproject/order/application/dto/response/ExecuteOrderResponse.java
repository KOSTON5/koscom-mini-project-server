package kr.co.koscom.miniproject.order.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import kr.co.koscom.miniproject.order.domain.vo.OrderCondition;
import kr.co.koscom.miniproject.order.domain.vo.OrderStatus;
import kr.co.koscom.miniproject.order.domain.vo.OrderType;

/**
 * description : 주문 실행 응답
 *
 * @param orderId          주문 ID
 * @param ticker           종목 코드 (삼성전자: 005930)
 * @param orderType        주문 유형 (MARKET, LIMIT 등)
 * @param orderCondition   매수/매도 여부 (BUY, SELL)
 * @param orderStatus      주문 상태 (PENDING, MATCHED)
 * @param executedPrice    체결 가격 (시장가일 경우 변동 가능)
 * @param executedQuantity 체결 수량 (부분 체결 가능)
 * @param totalAmount      총 거래 금액 = executedPrice * executedQuantity
 * @param executionTime    체결 시간
 * @param expirationTime   주문 만료 시간 (지정가 주문 등)
 */
public record ExecuteOrderResponse(
    Long orderId, String ticker,
    OrderType orderType, OrderCondition orderCondition, OrderStatus orderStatus,
    Integer executedPrice, Integer executedQuantity, Integer totalAmount,
    LocalDateTime executionTime, LocalDate expirationTime
) {

    public static ExecuteOrderResponse from(OrderEntity order) {
        return new ExecuteOrderResponse(
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
