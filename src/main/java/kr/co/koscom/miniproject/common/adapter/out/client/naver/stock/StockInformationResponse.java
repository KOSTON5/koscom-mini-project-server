package kr.co.koscom.miniproject.common.adapter.out.client.naver.stock;

import lombok.Builder;

@Builder(toBuilder = true)
public record StockInformationResponse(
    String ticker,
    String name,
    Integer currentPrice,
    Long tradingVolume,
    Double fluctuationRate
) {

}
