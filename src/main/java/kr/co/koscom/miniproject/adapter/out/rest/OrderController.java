package kr.co.koscom.miniproject.adapter.out.rest;

import kr.co.koscom.miniproject.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController implements OrderControllerDocs {

    private final StockService orderApplicationService;

    @Override
    public void purchaseStock() {

    }

    @Override
    public void sellStock() {

    }
}
