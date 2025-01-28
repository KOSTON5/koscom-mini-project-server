package kr.co.koscom.miniproject.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.koscom.miniproject.application.dto.request.CancelOrderRequest;
import kr.co.koscom.miniproject.application.dto.request.ExecuteOrderRequest;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "주문 API", description = "주식 주문 로직과 관련된 API 명세서입니다.")
@RequestMapping("/api/order")
public interface OrderControllerDocs {

    @Operation(summary = "주문 체결", description = "주문을 체결 합니다.")
    void executeOrder(ExecuteOrderRequest executeOrderRequest);

    @Operation(summary = "주문 취소", description = "주문을 취소 합니다.")
    void cancelOrder(CancelOrderRequest cancelOrderRequest);
}
