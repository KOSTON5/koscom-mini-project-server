package kr.co.koscom.miniproject.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .codecs(
	clientCodecConfigurer ->
	    clientCodecConfigurer
	        .defaultCodecs()
	        .maxInMemorySize(16 * 1024 * 1024)
            )
            .build();
    }
}
