package fan.fancy.log.printer;

import fan.fancy.log.model.FancyLogEvent;

/**
 * 日志输出器接口.
 *
 * @author Fan
 */
public interface FancyLogPrinter {

    void print(FancyLogEvent event);
}
