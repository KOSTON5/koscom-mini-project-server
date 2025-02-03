package kr.co.koscom.miniproject.stock.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.koscom.miniproject.stock.application.dto.response.RetrieveStockChartResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Stock API", description = "주식 정보를 제공하는 API 명세서입니다.")
@RequestMapping("/api/stock")
public interface StockControllerDocs {

    @Operation(summary = "주식 차트", description = "주식 차트를 조회하는 API 입니다.")
    @GetMapping("/chart")
    ResponseEntity<RetrieveStockChartResponse> retrieveStockChart();
}
