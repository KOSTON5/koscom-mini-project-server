package kr.co.koscom.miniproject.common.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NAVER_CLOVA_STT("CV0001", "네이버 Clova STT 중 오류가 발생했습니다"),

    OPEN_AI_CHAT("OA0001", "OpenAI 통신 중 오류가 발생했습니다."),

    USER_NOT_FOUND("US0001", "사용자를 찾을 수 없습니다."),
    USER_BALANCE_NOT_ENOUGH("US0002", "계좌 잔고가 부족합니다."),

    ORDER_NOT_FOUND("OR0001", "주문을 찾을 수 없습니다."),
    MARKET_BUY_ORDER_FAIL("OR0002", "시장가 매수 주문에 실패했습니다."),
    MARKET_SELL_ORDER_FAIL("OR0003", "시장가 매도 주문에 실패했습니다."),

    STOCK_NOT_FOUND("ST0001", "주식을 찾을 수 없습니다."),
    ;


    private final String code;
    private final String message;
}
