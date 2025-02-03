package kr.co.koscom.miniproject.common.application.dto.response;

public record AnalyzeTextResponse(
    String orderType, String ticker, String stockName, int quantity,
    int price, String orderCondition
) {
}
