package kr.co.koscom.miniproject.common.infrastructure.config;

import kr.co.koscom.miniproject.common.infrastructure.resolver.UserIdParameterResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@Slf4j
@Configuration
@ComponentScan(basePackages = "kr.co.koscom.miniproject")
public class WebFluxConfig implements WebFluxConfigurer {

    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        log.info("WebFluxConfig : configureArgumentResolvers");
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
