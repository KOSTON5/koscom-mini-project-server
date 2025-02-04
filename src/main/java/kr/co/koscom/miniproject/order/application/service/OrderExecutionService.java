package kr.co.koscom.miniproject.order.application.service;

import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import kr.co.koscom.miniproject.order.domain.service.OrderService;
import kr.co.koscom.miniproject.order.domain.vo.OrderStatus;
import kr.co.koscom.miniproject.user.application.service.UserQueryService;
import kr.co.koscom.miniproject.user.domain.entity.UserEntity;
import kr.co.koscom.miniproject.user.domain.service.UserService;
import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.common.infrastructure.exception.ErrorCode;
import kr.co.koscom.miniproject.common.infrastructure.exception.OrderFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 매매 체결 (도메인에 강하게 연관된) 과 관련된 실행 로직을 담당하는 서비스
 */
@Slf4j
@RequiredArgsConstructor
@ApplicationService
@Transactional
public class OrderExecutionService {

    private final UserService userService;
    private final UserQueryService userQueryService;

    private final OrderService orderService;
    private final OrderQueryService orderQueryService;

    public void executeBuyOrder(final Long userId, final Long orderId) {
        log.info("Executing Market Buy Order for Order ID: {}", orderId);
        OrderEntity order = orderQueryService.findById(orderId);
        UserEntity user = userQueryService.findById(userId);

        Integer totalPrice = order.getPrice() * order.getQuantity();
        log.info("Current Balance : {} \n, Total Market Buy Price: {}", user.getBalance(),
            totalPrice);

        try {
            userService.decreaseBalance(user, totalPrice);
        } catch (Exception exception) {
            orderService.changeOrderStatus(order, OrderStatus.FAILED);
            throw new OrderFailException(ErrorCode.MARKET_BUY_ORDER_FAIL);
        }

        log.info("Market Buy Order Executed Successfully, After Balance: {}", user.getBalance());
        OrderEntity updatedOrder = orderService.executeOrder(order);
        user.addOrder(updatedOrder);
        userQueryService.save(user);
    }

    public void executeSellOrder(Long userId, Long orderId) {
        log.info("Executing Market Sell Order for Order ID: {}", orderId);

        OrderEntity order = orderQueryService.findById(orderId);
        UserEntity user = userQueryService.findById(userId);

        Integer totalPrice = order.getPrice() * order.getQuantity();
        log.info("Current Balance : {} \n, Total Market Sell Price: {}", user.getBalance(), totalPrice);

        try {
            userService.increaseBalance(user, totalPrice);
        } catch (Exception exception) {
            orderService.changeOrderStatus(order, OrderStatus.FAILED);
            throw new OrderFailException(ErrorCode.MARKET_SELL_ORDER_FAIL);
        }
        log.info("Market Sell Order Executed Successfully, After Balance: {}", user.getBalance());

        OrderEntity updatedOrder = orderService.executeOrder(order);
        user.addOrder(updatedOrder);
        userQueryService.save(user);
    }
}
