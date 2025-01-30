package kr.co.koscom.miniproject.application.service;

import kr.co.koscom.miniproject.application.dto.request.CancelOrderRequest;
import kr.co.koscom.miniproject.application.dto.request.ExecuteOrderRequest;
import kr.co.koscom.miniproject.domain.order.service.OrderService;
import kr.co.koscom.miniproject.domain.order.vo.OrderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderApplicationService {

    private final OrderService orderService;

    public Long executeOrder(ExecuteOrderRequest executeOrderRequest) {
        OrderType orderType = OrderType.from(executeOrderRequest.orderType());

        switch (orderType) {
            case BUY:
	// todo : 처리할 예정
	break;
            case SELL:
	// todo : 처리할 예정
	break;
            default:
        }

        return null;
    }

    public void cancelOrder(CancelOrderRequest cancelOrderRequest) {
        orderService.deleteOrder(cancelOrderRequest.orderId());
    }
}
