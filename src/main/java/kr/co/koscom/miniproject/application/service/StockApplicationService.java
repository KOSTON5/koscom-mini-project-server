package kr.co.koscom.miniproject.application.service;

import kr.co.koscom.miniproject.application.port.out.NaverStockClientPort;
import kr.co.koscom.miniproject.stock.domain.entity.StockEntity;
import kr.co.koscom.miniproject.infrastructure.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class StockApplicationService {

    private final NaverStockClientPort naverStockClientPort;
    private final StockQueryService stockQueryService;

    public Integer updateMarketPrice(String ticker) {
        Integer realtimeMarketPrice = naverStockClientPort
            .getStockPrice(ticker)
            .getFirstStockData()
            .openingPrice();

        StockEntity stock = stockQueryService.findByTicker(ticker);
        stock.updateCurrentPrice(realtimeMarketPrice);

        return realtimeMarketPrice;
    }

}
