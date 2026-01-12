package fan.fancy.log.printer;

import java.util.Arrays;

/**
 * 日常参数安全处理器.
 *
 * @author Fan
 */
public final class FancyLogSanitizer {

    private static final String ELLIPSIS = "...";

    private FancyLogSanitizer() {
    }

    public static String sanitize(Object value, int maxLength) {
        if (value == null) {
            return null;
        }

        String text;
        if (value.getClass().isArray()) {
            text = Arrays.deepToString((Object[]) value);
        } else {
            text = String.valueOf(value);
        }

        if (maxLength <= 0 || text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength) + ELLIPSIS;
    }
}
