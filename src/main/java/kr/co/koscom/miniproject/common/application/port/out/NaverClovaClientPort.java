package kr.co.koscom.miniproject.common.application.port.out;

public interface NaverClovaClientPort<T, R> {

    R sendRequest(T request);
}
