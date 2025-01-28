package kr.co.koscom.miniproject.application.port.out;

/**
 * 분리 인터페이스 패턴
 */
public interface OpenAiClientPort<T, R> {
    R chat(T request);
}
