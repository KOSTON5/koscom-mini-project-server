package kr.co.koscom.miniproject.application.service;

import kr.co.koscom.miniproject.stock.adapter.out.jpa.StockJpaRepository;
import kr.co.koscom.miniproject.stock.domain.entity.StockEntity;
import kr.co.koscom.miniproject.infrastructure.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class StockQueryService {

    private final StockJpaRepository stockJpaRepository;

    public StockEntity findByTicker(final String ticker) {
        return stockJpaRepository.findByTicker(ticker)
            .orElseThrow(() -> new IllegalArgumentException("Stock Not Found"));
    }
}
