package kr.co.koscom.miniproject.order.application.service;

import kr.co.koscom.miniproject.order.adapter.out.jpa.OrderJpaRepository;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class OrderQueryService {

    private final OrderJpaRepository orderJpaRepository;
    public OrderEntity findById(Long orderId) {
        return orderJpaRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    public void addToPendingQueue(OrderEntity order) {
        orderJpaRepository.save(order);
    }
}
