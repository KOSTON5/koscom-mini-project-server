package kr.co.koscom.miniproject.adapter.out.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "주문 API", description = "주식 주문 비즈니스 로직과 관련된 API 명세서입니다.")
@RequestMapping("/api/order")
public interface OrderControllerDocs {
    @Operation(summary = "주식 매수", description = "주식을 매수합니다.")
    void purchaseStock();

    @Operation(summary = "주식 매도", description = "주식을 매도합니다.")
    void sellStock();
}
