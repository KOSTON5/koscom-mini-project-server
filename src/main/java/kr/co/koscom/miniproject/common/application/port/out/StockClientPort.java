package kr.co.koscom.miniproject.common.application.port.out;

import kr.co.koscom.miniproject.common.adapter.out.client.naver.stock.NaverStockResponse;

public interface StockClientPort {
    NaverStockResponse scrapStock(String ticker);


}
