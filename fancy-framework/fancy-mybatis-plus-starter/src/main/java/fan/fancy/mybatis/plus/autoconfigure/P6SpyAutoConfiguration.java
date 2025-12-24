package fan.fancy.mybatis.plus.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.PropertySource;

/**
 * P6Spy 自动配置类.
 *
 * @author Fan
 */
@AutoConfiguration
@PropertySource("classpath:/p6spy.properties")
public class P6SpyAutoConfiguration {
}
