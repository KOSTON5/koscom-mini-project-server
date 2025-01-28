package kr.co.koscom.miniproject.adapter.out.client.openai;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record OpenAiResponse(
    @NotNull String orderType, @NotNull String ticker, @NotNull int quantity, @NotNull int price,
    @NotNull String orderCondition, @NotNull LocalDate expirationTime, @NotNull Long orderId
) {

}
