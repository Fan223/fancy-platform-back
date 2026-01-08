package fan.fancy.log.model;

import lombok.Builder;

/**
 * 日志事件模型.
 *
 * @author Fan
 */
@Builder
public record FancyLogEvent(
        // 服务名
        String serviceName,
        // 类全限定名
        String className,
        // 方法名
        String methodName,
        // 标签
        String tag,
        // 入参
        Object args,
        // 最大参数长度
        int maxArgsLength,
        // 返回结果
        Object result,
        // 最大返回结果长度
        int maxResultLength,
        // 耗时(毫秒)
        long costMs,
        // 异常对象
        Throwable exception
) {
}
