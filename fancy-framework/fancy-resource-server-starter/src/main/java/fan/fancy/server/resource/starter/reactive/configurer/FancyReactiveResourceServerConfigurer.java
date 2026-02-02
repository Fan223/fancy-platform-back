package fan.fancy.server.resource.starter.reactive.configurer;

import fan.fancy.server.resource.starter.reactive.handler.FancyReactiveAccessDeniedHandler;
import fan.fancy.server.resource.starter.reactive.handler.FancyReactiveAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

/**
 * {@link ConditionalOnWebApplication.Type#REACTIVE} 资源服务器配置器.
 *
 * @author Fan
 */
@RequiredArgsConstructor
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class FancyReactiveResourceServerConfigurer {

    private final FancyReactiveAuthenticationEntryPoint reactiveAuthenticationEntryPoint;

    private final FancyReactiveAccessDeniedHandler reactiveAccessDeniedHandler;

    private final Converter<Jwt, Mono<AbstractAuthenticationToken>> converter;

    public void configure(ServerHttpSecurity http) {
        http.oauth2ResourceServer(spec -> spec
                .jwt(jwtSpec -> jwtSpec
                        .jwtAuthenticationConverter(converter))
                .authenticationEntryPoint(reactiveAuthenticationEntryPoint)
                .accessDeniedHandler(reactiveAccessDeniedHandler)
        );
    }
}
