package kr.co.koscom.miniproject.stock.adapter.in.rest;

import kr.co.koscom.miniproject.stock.application.dto.request.CreateStockRequest;
import kr.co.koscom.miniproject.stock.application.dto.response.CreateStockResponse;
import kr.co.koscom.miniproject.stock.application.dto.response.RetrieveStockChartResponse;
import kr.co.koscom.miniproject.stock.application.service.StockApplicationService;
import kr.co.koscom.miniproject.stock.application.service.StockQueryService;
import kr.co.koscom.miniproject.stock.domain.entity.StockEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StockController implements StockControllerDocs {

    private final StockQueryService stockQueryService;
    private final StockApplicationService stockApplicationService;

    @Override
    public ResponseEntity<RetrieveStockChartResponse> retrieveStockChart() {
        return ResponseEntity.ok(stockQueryService.retrieveTop10StockChart());
    }

    @Override
    public ResponseEntity<CreateStockResponse> createStock(CreateStockRequest createStockRequest) {
        return ResponseEntity.ok(
            stockApplicationService.createStock(createStockRequest)
        );
    }

    @Override
    public ResponseEntity<StockEntity> createStockByTicker(String ticker) {
        return ResponseEntity.ok(
            stockApplicationService.createStock(ticker)
        );
    }
}
