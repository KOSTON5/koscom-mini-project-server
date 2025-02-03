package kr.co.koscom.miniproject.order.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.koscom.miniproject.order.application.dto.request.AnalyzeOrderRequest;
import kr.co.koscom.miniproject.order.application.dto.request.ExecuteOrderRequest;
import kr.co.koscom.miniproject.order.application.dto.response.AnalyzeOrderResponse;
import kr.co.koscom.miniproject.order.application.dto.request.CancelOrderRequest;
import kr.co.koscom.miniproject.common.infrastructure.annotation.CurrentUserId;
import kr.co.koscom.miniproject.order.application.dto.response.ExecuteOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "주문 API", description = "주식 주문 관련 API 명세서")
@RequestMapping("/api/orders")
public interface OrderControllerDocs {

    @Operation(summary = "주문 정보를 분석", description = "주문 정보를 LLM 서버에 분석 하고 결과를 반환합니다.")
    ResponseEntity<AnalyzeOrderResponse> analyzeOrder(
        @RequestBody AnalyzeOrderRequest analyzeOrderRequest
    );

    @Operation(summary = "주문 취소", description = "주문을 취소 합니다.")
    @PostMapping("/cancel")
    ResponseEntity<Void> cancelOrder(
        @RequestBody CancelOrderRequest cancelOrderRequest
    );

    @Operation(summary = "시장가 매수", description = "시장가 매수 주문을 체결 합니다.")
    @PostMapping("/market/buy")
    ResponseEntity<ExecuteOrderResponse> executeMarketBuyOrder(
        @CurrentUserId Long userId,
        @RequestBody ExecuteOrderRequest executeMarketOrderRequest
    );

    @Operation(summary = "시장가 매도", description = "시장가 매도 주문을 체결 합니다.")
    @PostMapping("/market/sell")
    ResponseEntity<ExecuteOrderResponse> executeMarketSellOrder(
        @CurrentUserId Long userId,
        @RequestBody ExecuteOrderRequest executeMarketOrderRequest
    );

    @Operation(summary = "지정가 매수", description = "지정가 매수 주문을 체결 합니다.")
    @PostMapping("/limit/buy")
    ResponseEntity<ExecuteOrderResponse> executeLimitBuyOrder(
        @CurrentUserId Long userId,
        @RequestBody ExecuteOrderRequest executeOrderRequest
    );

    @Operation(summary = "지정가 매도", description = "지정가 매도 주문을 체결 합니다.")
    @PostMapping("/limit/sell")
    ResponseEntity<ExecuteOrderResponse> executeLimitSellOrder(
        @CurrentUserId Long userId,
        @RequestBody ExecuteOrderRequest executeOrderRequest
    );
}

//    @PostMapping("/buy")
//    ResponseEntity<Void> executeBuyOrder(ExecuteOrderRequest executeOrderRequest);
//
//    @Operation(summary = "매도 주문 체결", description = "매도 주문을 체결 합니다.")
//    @PostMapping("/sell")
//    ResponseEntity<Void> executeSellOrder(ExecuteOrderRequest executeOrderRequest);
