package kr.co.koscom.miniproject.stock.infrastructure.exception;

import kr.co.koscom.miniproject.infrastructure.exception.CustomException;
import kr.co.koscom.miniproject.infrastructure.exception.ErrorCode;

public class StockNotFoundException extends CustomException {

    public StockNotFoundException() {
        super(ErrorCode.STOCK_NOT_FOUND);
    }
}
