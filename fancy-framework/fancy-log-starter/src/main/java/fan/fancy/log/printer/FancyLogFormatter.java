package fan.fancy.log.printer;

import java.util.Arrays;

/**
 * 格式化.
 *
 * @author Fan
 */
public final class FancyLogFormatter {

    private static final String ELLIPSIS = "...";

    private FancyLogFormatter() {
    }

    public static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value;
    }

    public static String formatArgs(Object[] args, int maxLength) {
        return sanitize(args, maxLength);
    }

    public static String formatResult(Object result, int maxLength) {
        return sanitize(result, maxLength);
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
