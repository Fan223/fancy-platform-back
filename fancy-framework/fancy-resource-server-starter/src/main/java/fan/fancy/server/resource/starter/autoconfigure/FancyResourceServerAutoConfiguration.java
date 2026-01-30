package fan.fancy.server.resource.starter.autoconfigure;

import fan.fancy.server.resource.starter.function.FancyAuthorizeCustomizer;
import fan.fancy.server.resource.starter.servlet.FancyAccessDeniedHandler;
import fan.fancy.server.resource.starter.servlet.FancyAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

/**
 * {@link ConditionalOnWebApplication.Type#SERVLET} 资源服务器自动配置类.
 *
 * @author Fan
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class FancyResourceServerAutoConfiguration {

    private final FancyAuthenticationEntryPoint authenticationEntryPoint;

    private final FancyAccessDeniedHandler accessDeniedHandler;

    private ObjectProvider<FancyAuthorizeCustomizer> authorizeCustomizers;

    @Bean
    public FancyResourceServerConfigurer fancyResourceServerConfigurer() {
        return new FancyResourceServerConfigurer(
                authenticationEntryPoint,
                accessDeniedHandler,
                authorizeCustomizers
        );
    }

//    @Bean
//    SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) {
//        http.authorizeHttpRequests(registry -> {
//                    registry.requestMatchers("/api/**").permitAll();
//                    authorizeCustomizers.orderedStream()
//                            .forEach(customizer -> customizer.customize(registry));
//                    registry.anyRequest().authenticated();
//                })
//                .oauth2ResourceServer(configurer -> configurer
//                        .jwt(Customizer.withDefaults())
//                        .authenticationEntryPoint(authenticationEntryPoint)
//                        .accessDeniedHandler(accessDeniedHandler)
//                );
//        return http.build();
//    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 设置解析权限信息的前缀，设置为空是去掉前缀
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        // 设置权限信息在jwt claims中的key, 要和 Token 中一致
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
