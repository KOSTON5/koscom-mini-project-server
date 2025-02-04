package kr.co.koscom.miniproject.order.domain.service;

import kr.co.koscom.miniproject.order.adapter.out.jpa.OrderJpaRepository;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import kr.co.koscom.miniproject.order.domain.vo.OrderStatus;
import kr.co.koscom.miniproject.common.infrastructure.annotation.DomainService;
import kr.co.koscom.miniproject.common.infrastructure.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@DomainService
@Transactional(readOnly = true)
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;

    public Long save(OrderEntity orderEntity) {
        return orderJpaRepository.save(orderEntity).getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        orderJpaRepository.findById(orderId)
            .filter(orderEntity -> orderEntity.getOrderStatus() == OrderStatus.PENDING)
            .ifPresent(orderEntity -> orderEntity.cancel());
    }

    public OrderEntity findPendingOrder(Long orderId) {
        return orderJpaRepository.findByIdAndOrderStatus(orderId, OrderStatus.PENDING)
            .orElseThrow(() -> new OrderNotFoundException());
    }

    public void changeOrderStatus(OrderEntity order, OrderStatus orderStatus) {
        order.changeOrderStatus(orderStatus);
    }

    @Transactional
    public OrderEntity executeOrder(OrderEntity order) {
        log.info("OrderService : executeOrder() Start");
        order.match();

        log.info("OrderService : executeOrder() : OrderStatus {}", order.getOrderStatus());
        log.info("OrderService : executeOrder() : executionTime {}", order.getExecutionTime());
        return order;
    }
}
