package kr.co.koscom.miniproject.stock.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.koscom.miniproject.stock.application.dto.request.CreateStockRequest;
import kr.co.koscom.miniproject.stock.application.dto.response.CreateStockResponse;
import kr.co.koscom.miniproject.stock.application.dto.response.RetrieveStockChartResponse;
import kr.co.koscom.miniproject.stock.domain.entity.StockEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Stock API", description = "주식 정보를 제공하는 API 명세서입니다.")
@RequestMapping("/api/stock")
public interface StockControllerDocs {

    @Operation(summary = "주식 차트", description = "주식 차트를 조회하는 API 입니다.")
    @GetMapping("/chart")
    ResponseEntity<RetrieveStockChartResponse> retrieveStockChart();

    @Operation(summary = "주식 생성", description = "주식 정보를 생성하는 API 입니다.")
    @PostMapping
    ResponseEntity<CreateStockResponse> createStock(
        @RequestBody CreateStockRequest createStockRequest
    );

    @Operation(summary = "티커 번호로 주식 생성", description = "주식 정보를 생성하는 API 입니다.")
    @PostMapping("/ticker")
    ResponseEntity<StockEntity> createStockByTicker(
        @RequestParam("ticker") String ticker
    );
}
