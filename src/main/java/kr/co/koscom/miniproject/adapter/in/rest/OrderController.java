package kr.co.koscom.miniproject.adapter.in.rest;

import kr.co.koscom.miniproject.adapter.out.client.naverclova.NaverClovaSttRequest;
import kr.co.koscom.miniproject.application.dto.request.CancelOrderRequest;
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
        return null;
    }

    @Override
    public ResponseEntity<Void> executeOrder(ExecuteOrderRequest executeOrderRequest) {
        orderApplicationService.executeOrder(executeOrderRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> cancelOrder(CancelOrderRequest cancelOrderRequest) {
        orderApplicationService.cancelOrder(cancelOrderRequest);
        return ResponseEntity.ok().build();
    }

}
