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

    private static final Logger log = LogManager.getLogger(DefaultFancyLogPrinter.class);

    @Override
    public void print(FancyLogEvent event) {
        String args = FancyLogSanitizer.sanitize(event.args(), event.maxArgsLength());
        String result = FancyLogSanitizer.sanitize(event.result(), event.maxResultLength());
        Throwable exception = event.exception();

        if (exception != null) {
            log.error(
                    "[{}] {}-{}.{} cost={}ms, args={}, result={}",
                    event.tag(),
                    event.serviceName(),
                    event.className(),
                    event.methodName(),
                    event.costMs(),
                    args,
                    result,
                    exception // Log4j2 / SLF4J 会自动打印完整 stack trace
            );
        } else {
            log.info(
                    "[{}] {}-{}.{} cost={}ms, args={}, result={}",
                    event.tag(),
                    event.serviceName(),
                    event.className(),
                    event.methodName(),
                    event.costMs(),
                    args,
                    result
            );
        }
    }
}
