package kr.co.koscom.miniproject.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NAVER_CLOVA_STT("CV0001", "네이버 Clova STT 오류"),

    OPEN_AI_CHAT("OA0001", "Open AI 오류"),;

    private final String code;
    private final String message;
}
