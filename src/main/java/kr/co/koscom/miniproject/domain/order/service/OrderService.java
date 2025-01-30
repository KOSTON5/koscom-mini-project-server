package kr.co.koscom.miniproject.domain.order.service;

import kr.co.koscom.miniproject.adapter.out.jpa.OrderJpaRepository;
import kr.co.koscom.miniproject.domain.order.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;

    public Long createTemporalOrder(OrderEntity orderEntity) {
        return orderJpaRepository.save(orderEntity).getId();
    }

    public void deleteOrder(Long orderId) {
        orderJpaRepository.deleteById(orderId);
    }
}
