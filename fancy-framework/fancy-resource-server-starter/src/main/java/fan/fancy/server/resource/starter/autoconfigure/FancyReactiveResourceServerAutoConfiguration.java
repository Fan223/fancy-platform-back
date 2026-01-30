package fan.fancy.server.resource.starter.autoconfigure;

import fan.fancy.server.resource.starter.reactive.FancyReactiveAccessDeniedHandler;
import fan.fancy.server.resource.starter.reactive.FancyReactiveAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * {@link ConditionalOnWebApplication.Type#REACTIVE} 资源服务器自动配置类..
 *
 * @author Fan
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class FancyReactiveResourceServerAutoConfiguration {

    private final FancyReactiveAuthenticationEntryPoint reactiveAuthenticationEntryPoint;

    private final FancyReactiveAccessDeniedHandler reactiveAccessDeniedHandler;

    @Bean
    SecurityWebFilterChain reactiveResourceServerSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(spec -> spec
                        .pathMatchers("/api/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(spec -> spec
                        .jwt(jwtSpec -> jwtSpec
                                .jwtAuthenticationConverter(reactiveJwtAuthenticationConverter()))
                        .authenticationEntryPoint(reactiveAuthenticationEntryPoint)
                        .accessDeniedHandler(reactiveAccessDeniedHandler)
                );
        return http.build();
    }

    public Converter<Jwt, Mono<AbstractAuthenticationToken>> reactiveJwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 设置解析权限信息的前缀，设置为空是去掉前缀
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        // 设置权限信息在jwt claims中的key
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
