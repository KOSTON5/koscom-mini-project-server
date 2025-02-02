package kr.co.koscom.miniproject.adapter.out.client.naver.clova;

import jakarta.validation.constraints.NotNull;

public record NaverClovaSttResponse(
    @NotNull
    String text
) {

}
