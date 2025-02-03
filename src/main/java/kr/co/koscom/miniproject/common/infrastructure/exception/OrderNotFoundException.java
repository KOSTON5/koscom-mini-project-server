package kr.co.koscom.miniproject.common.infrastructure.exception;

public class OrderNotFoundException extends CustomException {

    public OrderNotFoundException() {
        super(ErrorCode.ORDER_NOT_FOUND);
    }
}
