package kr.co.koscom.miniproject.adapter.in.rest;

import kr.co.koscom.miniproject.application.dto.request.CancelOrderRequest;
import kr.co.koscom.miniproject.application.dto.request.ExecuteOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController implements OrderControllerDocs {

    /**
     * 임시로 저장한 orderId를 통해 주식을 매수 혹은 매도. 만약에 취소한다면 orderId를 넘겨주고 취소해야함
     */
    @Override
    public void executeOrder(ExecuteOrderRequest executeOrderRequest) {

    }

    @Override
    public void cancelOrder(CancelOrderRequest cancelOrderRequest) {

    }

}
