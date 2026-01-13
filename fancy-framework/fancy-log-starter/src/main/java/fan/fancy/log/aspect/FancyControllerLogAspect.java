package fan.fancy.log.aspect;

import fan.fancy.log.annotation.FancyLog;
import fan.fancy.log.autoconfigure.FancyLogProperties;
import fan.fancy.log.model.FancyLogEvent;
import fan.fancy.log.printer.FancyLogPrinter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 控制器切面.
 *
 * @author Fan
 */
@Aspect
@Order
public class FancyControllerLogAspect {

    private final FancyLogPrinter printer;

    private final FancyLogProperties properties;

    public FancyControllerLogAspect(FancyLogPrinter printer, FancyLogProperties properties) {
        this.printer = printer;
        this.properties = properties;
    }

    @Around("within(@org.springframework.web.bind.annotation.RestController *) || within(@org.springframework.stereotype.Controller *)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        FancyLog fancyLog = AnnotatedElementUtils.findMergedAnnotation(method, FancyLog.class);

        // 如果方法或类上存在 @FancyLog 注解则跳过
        if (fancyLog != null) {
            return joinPoint.proceed();
        }

        long start = System.nanoTime();
        Object result = null;
        Throwable exception = null;

        try {
            result = joinPoint.proceed();
            return result;
        } catch (Throwable ex) {
            exception = ex;
            throw ex;
        } finally {
            long costMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            publishEvent(joinPoint, result, exception, costMs);
        }
    }

    private void publishEvent(ProceedingJoinPoint joinPoint, Object result, Throwable exception, long costMs) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        FancyLogEvent event = FancyLogEvent.builder()
                .serviceName(properties.getServiceName())
                .className(signature.getDeclaringTypeName())
                .methodName(signature.getName())
                .args(joinPoint.getArgs())
                .maxArgsLength(properties.getMaxArgLength())
                .result(result)
                .maxResultLength(properties.getMaxResultLength())
                .costMs(costMs).exception(exception)
                .build();
        printer.print(event);
    }
}
