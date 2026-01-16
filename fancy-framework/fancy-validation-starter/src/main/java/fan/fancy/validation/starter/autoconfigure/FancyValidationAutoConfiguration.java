package fan.fancy.validation.starter.autoconfigure;

import fan.fancy.validation.starter.advice.FancyValidationExceptionAdvice;
import fan.fancy.validation.starter.service.FancyValidatorService;
import jakarta.validation.Validator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 参数校验自动配置类.
 *
 * @author Fan
 */
@AutoConfiguration
public class FancyValidationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public FancyValidationExceptionAdvice fancyValidationExceptionAdvice() {
        return new FancyValidationExceptionAdvice();
    }

    @Bean
    @ConditionalOnMissingBean
    public FancyValidatorService fancyValidatorService(Validator validator) {
        return new FancyValidatorService(validator);
    }
}
