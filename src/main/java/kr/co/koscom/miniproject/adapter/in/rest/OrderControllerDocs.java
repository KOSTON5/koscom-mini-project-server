package kr.co.koscom.miniproject.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.koscom.miniproject.adapter.out.client.naverclova.NaverClovaSttRequest;
import kr.co.koscom.miniproject.application.dto.response.AnalyzeOrderResponse;
import kr.co.koscom.miniproject.application.dto.request.CancelOrderRequest;
import kr.co.koscom.miniproject.application.dto.request.ExecuteOrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "주문 API", description = "주식 주문 로직과 관련된 API 명세서입니다.")
@RequestMapping("/api/order")
public interface OrderControllerDocs {

    @Operation(summary = "주문 정보를 분석", description = "주문 정보를 LLM 서버에 분석 하고 결과를 반환합니다.")
    ResponseEntity<AnalyzeOrderResponse> analyzeOrder(NaverClovaSttRequest naverClovaSttRequest);

    @Operation(summary = "매수 주문 체결", description = "매수 주문을 체결 합니다.")
    @PostMapping("/buy")
    ResponseEntity<Void> executeBuyOrder(ExecuteOrderRequest executeOrderRequest);

    @Operation(summary = "매도 주문 체결", description = "매도 주문을 체결 합니다.")
    @PostMapping("/sell")
    ResponseEntity<Void> executeSellOrder(ExecuteOrderRequest executeOrderRequest);

    @Operation(summary = "주문 취소", description = "주문을 취소 합니다.")
    @PostMapping("/cancel")
    ResponseEntity<Void> cancelOrder(CancelOrderRequest cancelOrderRequest);
}
