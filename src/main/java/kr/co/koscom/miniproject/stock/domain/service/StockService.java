package kr.co.koscom.miniproject.stock.domain.service;

import kr.co.koscom.miniproject.stock.adapter.out.jpa.StockJpaRepository;
import kr.co.koscom.miniproject.stock.domain.entity.StockEntity;
import kr.co.koscom.miniproject.common.infrastructure.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class StockService {

    private final StockJpaRepository stockJpaRepository;

    public StockEntity findStockByTicker(String ticker) {
        return stockJpaRepository.findByTicker(ticker)
            .orElseThrow(() -> new IllegalArgumentException("Stock not found"));
    }
}
