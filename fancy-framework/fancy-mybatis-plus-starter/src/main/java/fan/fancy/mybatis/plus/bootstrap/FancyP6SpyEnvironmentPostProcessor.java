package fan.fancy.mybatis.plus.bootstrap;

import fan.fancy.mybatis.plus.properties.FancyP6SpyProperties;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * P6Spy 环境后置处理器.
 *
 * @author Fan
 */
public class FancyP6SpyEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, @NonNull SpringApplication application) {
        String logFormat = environment.getProperty("decorator.datasource.p6spy.log-format", FancyP6SpyProperties.LOG_FORMAT);
        System.setProperty("decorator.datasource.p6spy.log-format", logFormat);
    }
}
