package kr.co.koscom.miniproject.domain.order.service;

import kr.co.koscom.miniproject.adapter.out.jpa.OrderJpaRepository;
import kr.co.koscom.miniproject.domain.order.entity.OrderEntity;
import kr.co.koscom.miniproject.domain.order.vo.OrderStatus;
import kr.co.koscom.miniproject.infrastructure.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        // todo : Exception 변경 할 예정
        return orderJpaRepository.findByIdAndOrderStatus(orderId, OrderStatus.PENDING)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    public void changeOrderStatus(OrderEntity order, OrderStatus orderStatus) {
        order.changeOrderStatus(orderStatus);
    }
}
