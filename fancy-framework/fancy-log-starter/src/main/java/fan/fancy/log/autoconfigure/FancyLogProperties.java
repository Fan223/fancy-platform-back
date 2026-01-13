package fan.fancy.log.autoconfigure;

import fan.fancy.log.annotation.FancyLog;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志配置.
 *
 * @author Fan
 */
@Data
@ConfigurationProperties(prefix = "fan.fancy.log")
public class FancyLogProperties {

    /**
     * 是否启用 {@link FancyLog}.
     */
    private boolean enabled = true;

    /**
     * 服务名.
     */
    @NotBlank
    private String serviceName;

    /**
     * 慢方法阈值(毫秒)
     */
//    @Min(0)
    private long slowThresholdMs = 500;

    /**
     * 参数最大长度（字符）
     */
//    @Min(0)
//    @Max(100_000)
    private int maxArgsLength = 2048;

    /**
     * 返回值最大长度（字符）
     */
//    @Min(0)
//    @Max(100_000)
    private int maxResultLength = 2048;

    /**
     * 是否记录方法参数
     */
    private boolean logArgs = true;

    /**
     * 是否记录返回值
     */
    private boolean logResult = true;

    /**
     * 日志采样率(0.0 - 1.0)
     */
//    @Min(0)
//    @Max(1)
    private double sampleRate = 1.0;
}
