package kr.co.koscom.miniproject.stock.infrastructure.exception;

import kr.co.koscom.miniproject.common.infrastructure.exception.CustomException;
import kr.co.koscom.miniproject.common.infrastructure.exception.ErrorCode;

public class StockNotFoundException extends CustomException {

    public StockNotFoundException() {
        super(ErrorCode.STOCK_NOT_FOUND);
    }
}
