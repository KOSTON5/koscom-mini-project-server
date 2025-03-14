package kr.co.koscom.miniproject.common.infrastructure.handler;

import kr.co.koscom.miniproject.common.infrastructure.exception.CustomException;
import kr.co.koscom.miniproject.common.infrastructure.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException exception) {
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.fromErrorCode(exception.getErrorCode()));
    }
}
