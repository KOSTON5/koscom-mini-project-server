package kr.co.koscom.miniproject.infrastructure.exception;

import lombok.Getter;

@Getter
public class UserBalanceNotEnoughException extends CustomException {

    public UserBalanceNotEnoughException() {
        super(ErrorCode.USER_BALANCE_NOT_ENOUGH);
    }
}
