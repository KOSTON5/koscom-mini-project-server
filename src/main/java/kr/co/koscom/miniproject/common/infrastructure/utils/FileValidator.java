package kr.co.koscom.miniproject.common.infrastructure.utils;

import java.util.Arrays;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {

    private static final String[] AUDIO_TYPES = {"audio/wav", "audio/mpeg"};
    private static final long MAX_SIZE = 10000000;

    public static boolean isNotAudioType(MultipartFile audio) {
        String contentType = audio.getContentType();
        boolean isMatched = Arrays.stream(AUDIO_TYPES).anyMatch(type -> type.equals(contentType));

        if (!isMatched) {
            return false;
        }

        return true;
    }

    public static boolean isSizeOver(MultipartFile audio) {
        if (audio.getSize() > MAX_SIZE) {
            return true;
        }

        return false;
    }
}
