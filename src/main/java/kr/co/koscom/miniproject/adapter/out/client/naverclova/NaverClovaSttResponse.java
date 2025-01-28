package kr.co.koscom.miniproject.adapter.out.client.naverclova;

import jakarta.validation.constraints.NotNull;

public record NaverClovaSttResponse(
    @NotNull
    String text
) {

}
