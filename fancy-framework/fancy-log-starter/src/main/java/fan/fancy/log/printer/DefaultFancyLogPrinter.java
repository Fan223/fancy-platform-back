package fan.fancy.log.printer;

import fan.fancy.log.model.FancyLogEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 默认日志输出器.
 *
 * @author Fan
 */
public class DefaultFancyLogPrinter implements FancyLogPrinter {

    private static final Logger LOGGER = LogManager.getLogger(DefaultFancyLogPrinter.class);

    @Override
    public void print(FancyLogEvent event) {
        String tag = FancyLogFormatter.normalize(event.tag());
        String service = FancyLogFormatter.normalize(event.serviceName());
        String args = FancyLogFormatter.formatArgs(event.args(), event.maxArgsLength());
        String result = FancyLogFormatter.formatResult(event.result(), event.maxResultLength());

        StringBuilder msg = new StringBuilder(128);
        if (tag != null) {
            msg.append('[').append(tag).append("] ");
        }

        if (service != null) {
            msg.append(service).append('-');
        }

        msg.append(event.className())
                .append('.')
                .append(event.methodName())
                .append(" 耗时=")
                .append(event.costMs())
                .append("ms");

        if (args != null) {
            msg.append(", 入参=").append(args);
        }

        if (result != null) {
            msg.append(", 返回结果=").append(result);
        }

        Throwable exception = event.exception();
        if (exception == null) {
            LOGGER.info("{}", msg);
        } else {
            // 会自动打印完整 stack trace
            LOGGER.error("{}", msg, exception);
        }
    }
}
