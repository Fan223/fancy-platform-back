package fan.fancy.server.resource.starter.autoconfigure;

import fan.fancy.server.resource.starter.function.FancyAuthorizeCustomizer;
import fan.fancy.server.resource.starter.servlet.FancyAccessDeniedHandler;
import fan.fancy.server.resource.starter.servlet.FancyAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 *
 * @author Fan
 */
@AllArgsConstructor
public class FancyResourceServerConfigurer
        extends AbstractHttpConfigurer<FancyResourceServerConfigurer, HttpSecurity> {

    private final FancyAuthenticationEntryPoint authenticationEntryPoint;
    private final FancyAccessDeniedHandler accessDeniedHandler;
    private final ObjectProvider<FancyAuthorizeCustomizer> authorizeCustomizers;

    @Override
    public void init(HttpSecurity http) {

        // 1. 配置 Resource Server 能力
        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults())
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        );

        // 2. 注入默认 + 扩展授权规则（不做兜底）
        http.authorizeHttpRequests(registry -> {
            registry.requestMatchers("/api/**").permitAll();

            authorizeCustomizers.orderedStream()
                    .forEach(c -> c.customize(registry));
            registry.anyRequest().authenticated();
        });
    }
}
