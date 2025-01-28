package kr.co.koscom.miniproject.adapter.out.client.naverclova;

import org.springframework.web.multipart.MultipartFile;

public record NaverClovaSttRequest(
    MultipartFile audio
) {

}
