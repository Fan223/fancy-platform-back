package fan.fancy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关启动类.
 *
 * @author Fan
 */
@SpringBootApplication
@EnableDiscoveryClient
public class FancyGatewayApplication {
    static void main() {
        SpringApplication.run(FancyGatewayApplication.class);
    }
}
