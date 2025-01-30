package kr.co.koscom.miniproject.adapter.out.jpa;

import kr.co.koscom.miniproject.domain.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

}
