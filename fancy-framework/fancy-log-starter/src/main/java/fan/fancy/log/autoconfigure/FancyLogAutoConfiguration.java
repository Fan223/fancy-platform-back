package fan.fancy.log.autoconfigure;

import fan.fancy.log.aspect.FancyLogAspect;
import fan.fancy.log.printer.DefaultFancyLogPrinter;
import fan.fancy.log.printer.FancyLogPrinter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 日志自动配置类.
 *
 * @author Fan
 */
@AutoConfiguration
@EnableConfigurationProperties(FancyLogProperties.class)
public class FancyLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public FancyLogPrinter fancyLogPrinter() {
        return new DefaultFancyLogPrinter();
    }

    @Bean
    public FancyLogAspect fancyLogAspect(FancyLogPrinter printer, FancyLogProperties properties) {
        return new FancyLogAspect(printer, properties);
    }
}
