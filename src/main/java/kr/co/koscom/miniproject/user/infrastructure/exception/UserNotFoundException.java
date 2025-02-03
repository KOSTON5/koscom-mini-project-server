package kr.co.koscom.miniproject.user.infrastructure.exception;

import kr.co.koscom.miniproject.infrastructure.exception.CustomException;
import kr.co.koscom.miniproject.infrastructure.exception.ErrorCode;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
