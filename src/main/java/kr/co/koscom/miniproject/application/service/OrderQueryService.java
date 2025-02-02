package kr.co.koscom.miniproject.application.service;

import kr.co.koscom.miniproject.adapter.out.jpa.OrderJpaRepository;
import kr.co.koscom.miniproject.domain.order.entity.OrderEntity;
import kr.co.koscom.miniproject.infrastructure.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class OrderQueryService {

    private final OrderJpaRepository orderJpaRepository;
    public OrderEntity findById(Long orderId) {
        return orderJpaRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }
}
