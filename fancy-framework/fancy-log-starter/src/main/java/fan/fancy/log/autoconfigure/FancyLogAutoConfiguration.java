package fan.fancy.log.autoconfigure;

import fan.fancy.log.aspect.FancyLogAspect;
import fan.fancy.log.filter.TraceIdFilter;
import fan.fancy.log.printer.DefaultFancyLogPrinter;
import fan.fancy.log.printer.FancyLogPrinter;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

/**
 * 日志自动配置类.
 *
 * @author Fan
 */
@AutoConfiguration
@EnableConfigurationProperties(FancyLogProperties.class)
public class FancyLogAutoConfiguration {

    private final FancyLogProperties properties;
    @Value("${spring.application.name:}")
    private String applicationName;

    public FancyLogAutoConfiguration(FancyLogProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        if (!StringUtils.hasText(properties.getServiceName())) {
            if (StringUtils.hasText(applicationName)) {
                properties.setServiceName(applicationName);
            } else {
                properties.setServiceName("unknown-service");
            }
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public FancyLogPrinter fancyLogPrinter() {
        return new DefaultFancyLogPrinter();
    }

    @Bean
    @ConditionalOnMissingBean
    public FancyLogAspect fancyLogAspect(FancyLogPrinter printer, FancyLogProperties properties) {
        return new FancyLogAspect(printer, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<TraceIdFilter> traceIdFilter() {
        // 注册 TraceIdFilter
        FilterRegistrationBean<TraceIdFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new TraceIdFilter());
        // 指定拦截路径
        bean.addUrlPatterns("/*");
        bean.setDispatcherTypes(DispatcherType.REQUEST);
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
