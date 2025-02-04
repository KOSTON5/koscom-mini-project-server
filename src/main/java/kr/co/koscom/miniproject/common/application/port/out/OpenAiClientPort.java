package kr.co.koscom.miniproject.common.application.port.out;

/**
 * 분리 인터페이스 패턴
 * OpenAiClientPort (인터페이스) 는 Application 계층에 위치하고, 구현체는 Adapter 계층에 위치함으로써
 * 애플리케이션 서비스는 같은 패키지 내에 있는 클래스를 의존할 수 있다.
 */
public interface OpenAiClientPort<T, R> {
    R processRequest(T request);
}
