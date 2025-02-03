package kr.co.koscom.miniproject.order.domain.service;

import kr.co.koscom.miniproject.order.adapter.out.jpa.OrderJpaRepository;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import kr.co.koscom.miniproject.order.domain.vo.OrderStatus;
import kr.co.koscom.miniproject.common.infrastructure.annotation.DomainService;
import kr.co.koscom.miniproject.common.infrastructure.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;

    public Long saveOrder(OrderEntity orderEntity) {
        return orderJpaRepository.save(orderEntity).getId();
    }

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

    public void executeOrder(OrderEntity order) {
        order.match();
    }
}
