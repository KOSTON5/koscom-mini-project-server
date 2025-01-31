package kr.co.koscom.miniproject.application.dto.response;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;

@Builder(toBuilder = true)
public record AnalyzeOrderResponse(
    @NotNull String orderType, @NotNull String ticker, @NotNull String stockName, @NotNull int quantity, @NotNull int price,
    @NotNull String orderCondition, LocalDate expirationTime, @NotNull Long orderId
) {

}
