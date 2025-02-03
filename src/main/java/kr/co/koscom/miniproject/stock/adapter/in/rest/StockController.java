package kr.co.koscom.miniproject.stock.adapter.in.rest;

import kr.co.koscom.miniproject.stock.application.dto.response.RetrieveStockChartResponse;
import kr.co.koscom.miniproject.stock.application.service.StockQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StockController implements StockControllerDocs {

    private final StockQueryService stockQueryService;
    @Override
    public ResponseEntity<RetrieveStockChartResponse> retrieveStockChart() {
        return ResponseEntity.ok(stockQueryService.retrieveTop10StockChart());
    }
}
