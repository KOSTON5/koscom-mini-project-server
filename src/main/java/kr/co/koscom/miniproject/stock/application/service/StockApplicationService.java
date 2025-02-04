package kr.co.koscom.miniproject.stock.application.service;

import kr.co.koscom.miniproject.common.adapter.out.client.naver.stock.NaverStockResponse;
import kr.co.koscom.miniproject.common.adapter.out.client.naver.stock.StockInformationResponse;
import kr.co.koscom.miniproject.common.application.port.out.StockClientPort;
import kr.co.koscom.miniproject.common.infrastructure.annotation.ApplicationService;
import kr.co.koscom.miniproject.stock.application.dto.request.CreateStockRequest;
import kr.co.koscom.miniproject.stock.application.dto.response.CreateStockResponse;
import kr.co.koscom.miniproject.stock.domain.entity.StockEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@ApplicationService
@Transactional(readOnly = true)
public class StockApplicationService {

    private final StockClientPort naverStockClientPort;
    private final StockQueryService stockQueryService;

    public Integer retrieveRealtimeMarketPrice(String ticker) {
        Integer realtimeMarketPrice = naverStockClientPort
            .scrapStock(ticker)
            .getFirstStockData()
            .openingPrice();

        StockEntity stock = stockQueryService.findByTicker(ticker);

        if (stock == null) {
            stock = createStock(ticker);
        }

        stock.updateCurrentPrice(realtimeMarketPrice);

        return realtimeMarketPrice;
    }

    public StockInformationResponse getMarketInformation(String ticker) {
        NaverStockResponse naverStockResponse = naverStockClientPort.scrapStock(ticker);

        return StockInformationResponse.builder()
            .ticker(ticker)
            .name(naverStockResponse.getFirstStockData().name())
            .currentPrice(naverStockResponse.getFirstStockData().openingPrice())
            .tradingVolume(naverStockResponse.getFirstStockData().tradingVolume())
            .fluctuationRate(naverStockResponse.getFirstStockData().changeRate())
            .build();
    }

    @Transactional
    public StockEntity createStock(String ticker) {
        StockInformationResponse marketInformation = getMarketInformation(ticker);

        StockEntity stock = StockEntity.builder()
            .ticker(ticker)
            .name(marketInformation.name())
            .currentPrice(marketInformation.currentPrice())
            .tradingVolume(marketInformation.tradingVolume())
            .fluctuationRate(marketInformation.fluctuationRate())
            .build();

        return stockQueryService.save(stock);
    }

    public CreateStockResponse createStock(CreateStockRequest request) {
        StockEntity stock = StockEntity.builder()
            .ticker(request.ticker())
            .name(request.name())
            .currentPrice(request.currentPrice())
            .tradingVolume(request.tradingVolume())
            .fluctuationRate(request.fluctuationRate())
            .logoImageUrl(request.logoImageUrl())
            .build();

        return new CreateStockResponse(stockQueryService.save(stock).getId());
    }
}
