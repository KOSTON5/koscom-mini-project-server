package kr.co.koscom.miniproject.application.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record SpeechToTextRequest(
    MultipartFile audioFile
) {

}
