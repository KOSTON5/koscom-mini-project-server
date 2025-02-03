package kr.co.koscom.miniproject.stock.application.service;

import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.stock.adapter.out.jpa.StockJpaRepository;
import kr.co.koscom.miniproject.stock.application.dto.response.RetrieveStockChartResponse;
import kr.co.koscom.miniproject.stock.domain.entity.StockEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class StockQueryService {

    private final StockJpaRepository stockJpaRepository;

    public RetrieveStockChartResponse retrieveTop10StockChart() {
        return RetrieveStockChartResponse.from(
            stockJpaRepository.findTop10ByOrderByTradingVolumeDesc()
        );
    }

    public StockEntity findByTicker(String ticker) {
        return stockJpaRepository.findByTicker(ticker)
            .orElse(null);
    }

    public StockEntity save(StockEntity stock) {
        return stockJpaRepository.save(stock);
    }
}
