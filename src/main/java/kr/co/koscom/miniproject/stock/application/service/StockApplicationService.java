package kr.co.koscom.miniproject.stock.application.service;

import kr.co.koscom.miniproject.common.application.port.out.NaverStockClientPort;
import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.stock.domain.entity.StockEntity;
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
