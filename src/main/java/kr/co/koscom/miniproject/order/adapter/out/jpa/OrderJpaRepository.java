package kr.co.koscom.miniproject.order.adapter.out.jpa;

import java.util.Optional;
import kr.co.koscom.miniproject.order.domain.entity.OrderEntity;
import kr.co.koscom.miniproject.order.domain.vo.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByIdAndOrderStatus(Long orderId, OrderStatus orderStatus);
}
