package kr.co.koscom.miniproject.infrastructure.client;

/**
 * 분리 인터페이스 패턴
 */
public interface OpenAiClientPort<T, R> {
    R chat(T request);
}
