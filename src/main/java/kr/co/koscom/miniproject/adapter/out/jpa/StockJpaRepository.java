package kr.co.koscom.miniproject.adapter.out.jpa;

import java.util.Optional;
import kr.co.koscom.miniproject.domain.stock.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockJpaRepository extends JpaRepository<StockEntity, String> {

    Optional<StockEntity> findByTicker(String ticker);
}
