package fan.fancy.log.aspect;

import fan.fancy.log.annotation.FancyLog;
import fan.fancy.log.autoconfigure.FancyLogProperties;
import fan.fancy.log.model.FancyLogEvent;
import fan.fancy.log.printer.FancyLogPrinter;
import org.apache.logging.log4j.core.config.Order;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 日志切面.
 *
 * @author Fan
 */
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
public class FancyLogAspect {

    private final FancyLogPrinter printer;

    private final FancyLogProperties properties;

    public FancyLogAspect(FancyLogPrinter printer, FancyLogProperties properties) {
        this.printer = printer;
        this.properties = properties;
    }

    @Around("@within(fan.fancy.log.annotation.FancyLog) || @annotation(fan.fancy.log.annotation.FancyLog)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        FancyLog fancyLog = resolveFancyLog(joinPoint);
        if (fancyLog == null || !properties.isEnabled()) {
            return joinPoint.proceed();
        }

        long start = System.nanoTime();
        Throwable exception = null;
        Object result = null;

        try {
            result = joinPoint.proceed();
            return result;
        } catch (Throwable ex) {
            exception = ex;
            throw ex;
        } finally {
            long costMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            publishEvent(joinPoint, fancyLog, result, exception, costMs);
        }
    }

    /**
     * 解析 {@link FancyLog} 注解.
     *
     * @param joinPoint {@link ProceedingJoinPoint}
     * @return {@link FancyLog}
     */
    private FancyLog resolveFancyLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 先读取方法上的注解
        FancyLog fancyLog = AnnotationUtils.findAnnotation(method, FancyLog.class);
        if (fancyLog != null) {
            return fancyLog;
        }
        // 再读取类上的注解
        Class<?> targetClass = joinPoint.getTarget().getClass();
        return AnnotationUtils.findAnnotation(targetClass, FancyLog.class);
    }

    /**
     * 发布日志事件.
     *
     * @param joinPoint {@link ProceedingJoinPoint}
     * @param fancyLog  {@link FancyLog}
     * @param result    返回结果
     * @param exception {@link Throwable}
     * @param costMs    耗时(毫秒)
     */
    private void publishEvent(ProceedingJoinPoint joinPoint, FancyLog fancyLog, Object result, Throwable exception, long costMs) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        FancyLogEvent event = FancyLogEvent.builder()
                .serviceName(properties.getServiceName())
                .className(signature.getDeclaringTypeName())
                .methodName(signature.getName())
                .tag(fancyLog.tag())
                .args(fancyLog.logArgs() ? joinPoint.getArgs() : null)
                .maxArgsLength(properties.getMaxArgLength())
                .result(fancyLog.logResult() ? result : null)
                .maxResultLength(properties.getMaxResultLength())
                .costMs(costMs)
                .exception(exception)
                .build();
        printer.print(event);
    }
}
