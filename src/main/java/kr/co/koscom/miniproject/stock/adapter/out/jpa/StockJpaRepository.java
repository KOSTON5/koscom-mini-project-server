package kr.co.koscom.miniproject.stock.adapter.out.jpa;

import java.util.List;
import java.util.Optional;
import kr.co.koscom.miniproject.stock.domain.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockJpaRepository extends JpaRepository<StockEntity, String> {

    Optional<StockEntity> findByTicker(String ticker);

    List<StockEntity> findTop10ByOrderByTradingVolumeDesc();
}
