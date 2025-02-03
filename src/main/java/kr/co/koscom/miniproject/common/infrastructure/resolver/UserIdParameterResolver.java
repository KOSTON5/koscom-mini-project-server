package kr.co.koscom.miniproject.common.infrastructure.resolver;

import kr.co.koscom.miniproject.common.infrastructure.annotation.CurrentUserId;
import org.springframework.core.MethodParameter;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class UserIdParameterResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(CurrentUserId.class) != null;
    }

    @Override
    public Mono<Object> resolveArgument(
        MethodParameter parameter,
        BindingContext bindingContext,
        ServerWebExchange exchange
    ) {

        Long userId = Long.parseLong(exchange.getRequest().getHeaders().getFirst("X-USER-ID"));
        return Mono.justOrEmpty(userId);
    }

}
