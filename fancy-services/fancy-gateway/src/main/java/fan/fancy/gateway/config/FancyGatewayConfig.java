package fan.fancy.gateway.config;

import fan.fancy.server.resource.starter.reactive.configurer.FancyReactiveResourceServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * 网关配置类.
 *
 * @author Fan
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class FancyGatewayConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         FancyReactiveResourceServerConfigurer resourceServerConfigurer) {
        resourceServerConfigurer.configure(http);
        http.authorizeExchange(spec -> spec
                .pathMatchers("/api/**").permitAll()
                .anyExchange().authenticated());
        http.oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults());
        return http.build();
    }
}
