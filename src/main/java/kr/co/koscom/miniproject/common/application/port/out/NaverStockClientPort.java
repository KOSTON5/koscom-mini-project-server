package kr.co.koscom.miniproject.common.application.port.out;

import kr.co.koscom.miniproject.common.adapter.out.client.naver.stock.NaverStockPriceResponse;

public interface NaverStockClientPort {
    NaverStockPriceResponse getStockPrice(String ticker);
}
