package kr.co.koscom.miniproject.infrastructure.exception;

public class NaverClovaSttException extends CustomException {

    public NaverClovaSttException() {
        super(ErrorCode.NAVER_CLOVA_STT);
    }
}
