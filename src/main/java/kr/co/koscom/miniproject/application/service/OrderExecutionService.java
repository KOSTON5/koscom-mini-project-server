package kr.co.koscom.miniproject.application.service;

import kr.co.koscom.miniproject.domain.order.entity.OrderEntity;
import kr.co.koscom.miniproject.domain.order.service.OrderService;
import kr.co.koscom.miniproject.domain.order.vo.OrderStatus;
import kr.co.koscom.miniproject.domain.user.entity.UserEntity;
import kr.co.koscom.miniproject.domain.user.service.UserService;
import kr.co.koscom.miniproject.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.infrastructure.exception.ErrorCode;
import kr.co.koscom.miniproject.infrastructure.exception.OrderFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * 매매 체결 (도메인에 강하게 연관된) 과 관련된 실행 로직을 담당하는 서비스
 */
@RequiredArgsConstructor
@ApplicationService
@Transactional
public class OrderExecutionService {

    private final UserService userService;
    private final UserQueryService userQueryService;

    private final OrderService orderService;
    private final OrderQueryService orderQueryService;

    public void executeMarketBuyOrder(final Long userId, final Long orderId) {
        OrderEntity order = orderQueryService.findById(orderId);
        UserEntity user = userQueryService.findById(userId);

        Integer totalPrice = order.getPrice() * order.getQuantity();

        try {
            userService.decreaseBalance(user, totalPrice);
        } catch (Exception exception) {
            orderService.changeOrderStatus(order, OrderStatus.FAILED);
            throw new OrderFailException(ErrorCode.MARKET_BUY_ORDER_FAIL);
        }

        orderService.changeOrderStatus(order, OrderStatus.MATCHED);
    }
}
