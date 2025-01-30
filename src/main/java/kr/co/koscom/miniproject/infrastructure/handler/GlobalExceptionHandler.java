package kr.co.koscom.miniproject.infrastructure.handler;

import kr.co.koscom.miniproject.infrastructure.exception.CustomException;
import kr.co.koscom.miniproject.infrastructure.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException exception) {
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.fromErrorCode(exception.getErrorCode()));
    }
}
