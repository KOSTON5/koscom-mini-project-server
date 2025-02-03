package kr.co.koscom.miniproject.order.application.service;

import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.common.infrastructure.exception.OrderNotFoundException;
import kr.co.koscom.miniproject.order.adapter.out.jpa.OrderJpaRepository;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class OrderQueryService {

    private final OrderJpaRepository orderJpaRepository;

    public OrderEntity findById(Long orderId) {
        return orderJpaRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException());
    }

    public void addToPendingQueue(OrderEntity order) {
        orderJpaRepository.save(order);
    }
}
