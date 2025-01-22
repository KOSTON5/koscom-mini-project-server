package kr.co.koscom.miniproject.presentation.rest;

import kr.co.koscom.miniproject.application.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderApplicationService orderApplicationService;

    @PostMapping("/sell")
    public void sell() {

    }

    @PostMapping("/buy")
    public void buy() {

    }
}
