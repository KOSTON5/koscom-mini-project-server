package kr.co.koscom.miniproject.adapter.in.rest;

import kr.co.koscom.miniproject.adapter.out.client.naver.clova.NaverClovaSttRequest;
import kr.co.koscom.miniproject.application.dto.request.CancelOrderRequest;
import kr.co.koscom.miniproject.application.dto.request.ExecuteMarketOrderRequest;
import kr.co.koscom.miniproject.application.dto.request.ExecuteOrderRequest;
import kr.co.koscom.miniproject.application.dto.response.AnalyzeOrderResponse;
import kr.co.koscom.miniproject.application.service.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController implements OrderControllerDocs {

    private final OrderApplicationService orderApplicationService;

    @Override
    public ResponseEntity<AnalyzeOrderResponse> analyzeOrder(
        NaverClovaSttRequest naverClovaSttRequest) {
        return ResponseEntity.ok(orderApplicationService.processLLMOrder(naverClovaSttRequest));
    }

    @Override
    public ResponseEntity<Void> executeBuyOrder(ExecuteOrderRequest executeOrderRequest) {
        orderApplicationService.executeBuyOrder(executeOrderRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> executeSellOrder(ExecuteOrderRequest executeOrderRequest) {
        orderApplicationService.executeSellOrder(executeOrderRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> cancelOrder(CancelOrderRequest cancelOrderRequest) {
        orderApplicationService.cancelOrder(cancelOrderRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> executeMarketBuyOrder(
        Long currentUserId,
        ExecuteMarketOrderRequest executeMarketOrderRequest
    ) {
        orderApplicationService.executeMarketBuyOrder(currentUserId, executeMarketOrderRequest);
        return ResponseEntity.ok().build();
    }

}
