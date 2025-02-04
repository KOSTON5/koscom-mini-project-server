package kr.co.koscom.miniproject.user.application.dto.response;

import java.util.List;

public record RetrieveUserStocksResponse(
    Long userId,
    List<StockInfo> stocks
) {
    public record StockInfo(
        String stockName,
        Integer quantity
    ) {
    }
}
