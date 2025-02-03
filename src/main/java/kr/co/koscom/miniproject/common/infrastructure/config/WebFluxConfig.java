package kr.co.koscom.miniproject.common.infrastructure.config;

import kr.co.koscom.miniproject.common.infrastructure.resolver.UserIdParameterResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        configurer.addCustomResolver(new UserIdParameterResolver());
    }

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
