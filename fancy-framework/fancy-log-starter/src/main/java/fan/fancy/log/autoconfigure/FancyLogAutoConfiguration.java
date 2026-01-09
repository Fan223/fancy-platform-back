package fan.fancy.log.autoconfigure;

import fan.fancy.log.aspect.FancyLogAspect;
import fan.fancy.log.filter.TraceIdFilter;
import fan.fancy.log.printer.DefaultFancyLogPrinter;
import fan.fancy.log.printer.FancyLogPrinter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

/**
 * 日志自动配置类.
 *
 * @author Fan
 */
@AutoConfiguration
@EnableConfigurationProperties(FancyLogProperties.class)
public class FancyLogAutoConfiguration {

    static {
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
    }

    @Bean
    @ConditionalOnMissingBean
    public FancyLogPrinter fancyLogPrinter() {
        return new DefaultFancyLogPrinter();
    }

    @Bean
    public FancyLogAspect fancyLogAspect(FancyLogPrinter printer, FancyLogProperties properties) {
        return new FancyLogAspect(printer, properties);
    }

    @Bean
    public FilterRegistrationBean<TraceIdFilter> traceIdFilter() {
        // 注册 TraceIdFilter
        FilterRegistrationBean<TraceIdFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new TraceIdFilter());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        // 指定拦截路径
//        bean.addUrlPatterns("/*");
        return bean;
    }
}
