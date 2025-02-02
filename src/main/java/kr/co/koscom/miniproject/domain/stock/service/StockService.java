package kr.co.koscom.miniproject.domain.stock.service;

import kr.co.koscom.miniproject.adapter.out.jpa.StockJpaRepository;
import kr.co.koscom.miniproject.domain.stock.entity.StockEntity;
import kr.co.koscom.miniproject.infrastructure.annotation.DomainService;
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
