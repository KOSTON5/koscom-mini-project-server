package kr.co.koscom.miniproject.adapter.out.client.openai;

import lombok.Builder;

@Builder(toBuilder = true)
public record OpenAiRequest(
    String text
){

}
