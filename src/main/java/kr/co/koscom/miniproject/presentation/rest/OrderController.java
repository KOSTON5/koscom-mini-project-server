package kr.co.koscom.miniproject.presentation.rest;

import kr.co.koscom.miniproject.application.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController implements OrderControllerDocs {

    private final OrderApplicationService orderApplicationService;

    @Override
    public void purchaseStock() {

    }

    @Override
    public void sellStock() {

    }
}
