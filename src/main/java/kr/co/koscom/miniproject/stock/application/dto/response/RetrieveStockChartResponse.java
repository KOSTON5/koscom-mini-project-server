package kr.co.koscom.miniproject.stock.application.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.koscom.miniproject.stock.domain.entity.StockEntity;

public record RetrieveStockChartResponse(
    List<StockComponent> stockComponents
) {

    public static RetrieveStockChartResponse from(List<StockEntity> stockEntities) {
        return new RetrieveStockChartResponse(stockEntities.stream().map(
	stockEntity -> new StockComponent(
	    stockEntity.getName(),
	    stockEntity.getCurrentPrice(),
	    stockEntity.getTradingVolume(),
	    stockEntity.getFluctuationRate()
	)
            )
            .collect(Collectors.toList())
        );
    }

    record StockComponent(
        String stockName,
        Integer currentPrice,
        Integer tradingVolume,
        Double fluctuationRate
    ) {

    }
}
