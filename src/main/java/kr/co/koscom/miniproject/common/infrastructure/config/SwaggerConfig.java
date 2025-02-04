package kr.co.koscom.miniproject.common.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "코스콤 미니 프로젝트 5팀 API 명세",
        version = "v1",
        license = @License(name = "MIT License", url = "https://opensource.org/licenses/MIT"),
        contact = @io.swagger.v3.oas.annotations.info.Contact(
            name = "깃허브 주소",
            url = "https://github.com/packdev937/folio-monolithic"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080/", description = "로컬 서버 URL"),
        @Server(url = "http://kcloud_cst03-2574401.devtools.fin-ncloud.com/", description = "원격 서버 URL"),
    },
    security = @SecurityRequirement(name = "Authorization")
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .components(new Components());
    }
}