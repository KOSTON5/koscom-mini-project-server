package kr.co.koscom.miniproject.user.infrastructure.exception;

import kr.co.koscom.miniproject.common.infrastructure.exception.CustomException;
import kr.co.koscom.miniproject.common.infrastructure.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserBalanceNotEnoughException extends CustomException {

    public UserBalanceNotEnoughException() {
        super(ErrorCode.USER_BALANCE_NOT_ENOUGH);
    }
}
