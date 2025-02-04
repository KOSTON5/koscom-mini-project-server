package kr.co.koscom.miniproject.common.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Builder;

@Builder(toBuilder = true)
public record AnalyzeTextResponse(
    String commandType, String ticker, String stockName,
    int quantity, Integer price, String orderCondition,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate expirationTime
) {

    public boolean isPriceEmpty() {
        return price == null;
    }
}
