package kr.co.koscom.miniproject.infrastructure.utils;

import java.util.Arrays;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {

    private final String[] audioTypes = {"audio/wav", "audio/mpeg"};
    private final long MAX_SIZE = 10000000;

    public boolean isAudio(MultipartFile audio) {
        String contentType = audio.getContentType();
        boolean isMatched = Arrays.stream(audioTypes).anyMatch(type -> type.equals(contentType));

        if (isMatched) {
            return true;
        }

        return false;
    }

    public boolean isSizeNotOver(MultipartFile audio) {
        if (audio.getSize() > MAX_SIZE) {
            return false;
        }
        return true;
    }
}
