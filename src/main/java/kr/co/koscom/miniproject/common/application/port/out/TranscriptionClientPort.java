package kr.co.koscom.miniproject.common.application.port.out;

public interface TranscriptionClientPort<T, R> {

    R processRequest(T request);
}
