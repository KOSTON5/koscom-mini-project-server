package kr.co.koscom.miniproject.stock.application.dto.request;

public record CreateStockRequest(
    String ticker,
    String name,
    Integer currentPrice,
    Long tradingVolume,
    Double fluctuationRate,
    String logoImageUrl
) {


}
