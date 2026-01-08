package fan.fancy.log.annotation;

import java.lang.annotation.*;

/**
 * 日志注解.
 *
 * @author Fan
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FancyLog {

    /**
     * 是否记录参数
     */
    boolean logArgs() default true;

    /**
     * 是否记录返回值
     */
    boolean logResult() default true;

    /**
     * 最大参数长度(防止大对象)
     */
    int maxLength() default 2048;

    /**
     * 是否忽略异常（只打印 error 简要）
     */
    boolean ignoreException() default false;

    /**
     * 自定义业务标签
     */
    String tag() default "";
}
