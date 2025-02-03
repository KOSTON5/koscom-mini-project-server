package kr.co.koscom.miniproject.common.adapter.out.client.naver.clova;

import org.springframework.web.multipart.MultipartFile;

public record NaverClovaSttRequest(
    MultipartFile audio
) {

}
