package kr.co.koscom.miniproject.common.adapter.out.client.openai;

import java.io.IOException;
import kr.co.koscom.miniproject.common.application.port.out.OpenAiClientPort;
import kr.co.koscom.miniproject.common.infrastructure.exception.OpenAiChatException;
import kr.co.koscom.miniproject.common.infrastructure.exception.OpenAiTranscriptionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenAiTranscriptClient implements OpenAiClientPort<MultipartFile, String> {

    private final WebClient webClient;

    @Value("${openai.stt-base-url}")
    private String OPENAI_BASE_URL;

    @Value("${openai.api-key}")
    private String API_KEY;

    @Override
    public String chat(MultipartFile audioFile) {
        log.info("OpenAiTranscriptClient : chat() : audioFile {}", audioFile.getOriginalFilename());

        try {
            ByteArrayResource fileResource = new ByteArrayResource(audioFile.getBytes()) {
	@Override
	public String getFilename() {
	    return audioFile.getOriginalFilename();
	}
            };

            MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
            bodyBuilder.part("file", fileResource)
	.header("Content-Disposition",
	    "form-data; name=file; filename=" + audioFile.getOriginalFilename())
	.contentType(MediaType.APPLICATION_OCTET_STREAM);
            bodyBuilder.part("model", "whisper-1")
	.header("Content-Disposition", "form-data; name=model")
	.contentType(MediaType.TEXT_PLAIN);
            bodyBuilder.part("response_format", "text")
	.header("Content-Disposition", "form-data; name=response_format")
	.contentType(MediaType.TEXT_PLAIN);

            // Make POST request
            return webClient.post()
	.uri(OPENAI_BASE_URL)
	.header("Authorization", "Bearer " + API_KEY)
	.contentType(MediaType.MULTIPART_FORM_DATA)
	.bodyValue(bodyBuilder.build())
	.retrieve()
	.bodyToMono(String.class)
	.block();

        } catch (IOException exception) {
            log.error("STT 변환 실패: {}", exception.getMessage());
            throw new OpenAiTranscriptionException();
        }
    }
}
