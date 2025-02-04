package kr.co.koscom.miniproject.order.application.service;

import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.common.infrastructure.exception.OrderNotFoundException;
import kr.co.koscom.miniproject.order.adapter.out.jpa.OrderJpaRepository;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class OrderQueryService {

    private final OrderJpaRepository orderJpaRepository;

    public OrderEntity findById(Long orderId) {
        log.info("OrderQueryService : orderId {}", orderId);

        return orderJpaRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException());
    }

    @Transactional
    public void update(OrderEntity order) {
        log.info("OrderQueryService : update() : orderPrice {}", order.getPrice());
        orderJpaRepository.save(order);
    }
}
