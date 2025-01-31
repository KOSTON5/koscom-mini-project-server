package kr.co.koscom.miniproject.adapter.out.jpa;

import java.util.Optional;
import kr.co.koscom.miniproject.domain.order.entity.OrderEntity;
import kr.co.koscom.miniproject.domain.order.vo.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByIdAndOrderStatus(Long orderId, OrderStatus orderStatus);
}
