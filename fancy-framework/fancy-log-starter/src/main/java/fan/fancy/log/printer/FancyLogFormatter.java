package fan.fancy.log.printer;

/**
 * 格式化.
 *
 * @author Fan
 */
public final class FancyLogFormatter {

    private FancyLogFormatter() {
    }

    public static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value;
    }

    public static String formatArgs(Object[] args, int maxLength) {
        return FancyLogSanitizer.sanitize(args, maxLength);
    }

    public static String formatResult(Object result, int maxLength) {
        return FancyLogSanitizer.sanitize(result, maxLength);
    }
}
