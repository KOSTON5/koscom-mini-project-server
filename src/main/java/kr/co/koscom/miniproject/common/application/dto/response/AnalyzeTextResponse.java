package kr.co.koscom.miniproject.common.application.dto.response;

import lombok.Builder;

@Builder(toBuilder = true)
public record AnalyzeTextResponse(
    String orderType, String ticker, String stockName,
    int quantity, Integer price, String orderCondition
) {

    public boolean isPriceEmpty() {
        return price == null;
    }
}
