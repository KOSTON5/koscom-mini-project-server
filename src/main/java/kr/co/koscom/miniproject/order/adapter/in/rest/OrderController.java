package kr.co.koscom.miniproject.order.adapter.in.rest;

import kr.co.koscom.miniproject.order.application.dto.request.AnalyzeOrderRequest;
import kr.co.koscom.miniproject.order.application.dto.request.CancelOrderRequest;
import kr.co.koscom.miniproject.order.application.dto.request.ExecuteOrderRequest;
import kr.co.koscom.miniproject.order.application.dto.response.AnalyzeOrderResponse;
import kr.co.koscom.miniproject.order.application.dto.response.ExecuteOrderResponse;
import kr.co.koscom.miniproject.order.application.service.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController implements OrderControllerDocs {

    private final OrderApplicationService orderApplicationService;

    @Override
    public ResponseEntity<AnalyzeOrderResponse> analyzeOrder(
        AnalyzeOrderRequest analyzeOrderRequest) {
        return ResponseEntity.ok(
            orderApplicationService.processLLMOrder(analyzeOrderRequest)
        );
    }

    @Override
    public ResponseEntity<Void> cancelOrder(
        CancelOrderRequest cancelOrderRequest
    ) {
        orderApplicationService.cancelOrder(cancelOrderRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ExecuteOrderResponse> executeMarketBuyOrder(
        Long currentUserId,
        ExecuteOrderRequest executeMarketOrderRequest
    ) {
        return ResponseEntity.ok(orderApplicationService.executeMarketBuyOrder(currentUserId,
            executeMarketOrderRequest));
    }

    @Override
    public ResponseEntity<ExecuteOrderResponse> executeMarketSellOrder(Long userId,
        ExecuteOrderRequest executeMarketOrderRequest) {
        return ResponseEntity.ok(
            orderApplicationService.executeMarketSellOrder(userId, executeMarketOrderRequest));
    }

    @Override
    public ResponseEntity<ExecuteOrderResponse> executeLimitBuyOrder(
        Long userId,
        ExecuteOrderRequest executeOrderRequest
    ) {
        return ResponseEntity.ok(orderApplicationService.executeLimitBuyOrder(userId,
            executeOrderRequest));
    }

    @Override
    public ResponseEntity<ExecuteOrderResponse> executeLimitSellOrder(
        Long userId,
        ExecuteOrderRequest executeOrderRequest
    ) {
        return ResponseEntity.ok(orderApplicationService.executeLimitSellOrder(userId,
            executeOrderRequest));
    }
}

//@Override
//    public ResponseEntity<Void> executeBuyOrder(
//        ExecuteOrderRequest executeOrderRequest
//    ) {
//        orderApplicationService.executeBuyOrder(executeOrderRequest);
//        return ResponseEntity.ok().build();
//    }
//
//    @Override
//    public ResponseEntity<Void> executeSellOrder(
//        ExecuteOrderRequest executeOrderRequest
//    ) {
//        orderApplicationService.executeSellOrder(executeOrderRequest);
//        return ResponseEntity.ok().build();
//    }