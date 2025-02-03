package kr.co.koscom.miniproject.common.infrastructure.exception;

public class OrderFailException extends CustomException {

    public OrderFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
