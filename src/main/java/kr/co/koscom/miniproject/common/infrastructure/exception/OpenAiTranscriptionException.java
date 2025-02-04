package kr.co.koscom.miniproject.common.infrastructure.exception;

public class OpenAiTranscriptionException extends CustomException {

    public OpenAiTranscriptionException() {
        super(ErrorCode.OPEN_AI_CHAT);
    }

}
