package kr.co.koscom.miniproject.application.port.out;

import kr.co.koscom.miniproject.adapter.out.client.naver.stock.NaverStockPriceResponse;

public interface NaverStockClientPort {
    NaverStockPriceResponse getStockPrice(String ticker);
}
