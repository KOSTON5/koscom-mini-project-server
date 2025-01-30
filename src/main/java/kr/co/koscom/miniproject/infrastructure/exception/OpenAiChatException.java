package kr.co.koscom.miniproject.infrastructure.exception;

public class OpenAiChatException extends CustomException {

    public OpenAiChatException() {
        super(ErrorCode.OPEN_AI_CHAT);
    }

}
