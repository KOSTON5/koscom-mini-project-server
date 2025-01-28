package kr.co.koscom.miniproject.application.port.out;

public interface NaverClovaClientPort<T, R> {

    R sendRequest(T request);
}
