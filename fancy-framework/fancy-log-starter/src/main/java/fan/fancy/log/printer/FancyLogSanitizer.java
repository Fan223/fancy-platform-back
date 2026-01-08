package fan.fancy.log.printer;

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
        try {
            text = String.valueOf(value);
        } catch (Exception ex) {
            return "<toString-error:" + ex.getClass().getSimpleName() + ">";
        }

        if (maxLength <= 0 || text.length() <= maxLength) {
            return text;
        }
        int cut = Math.max(0, maxLength - ELLIPSIS.length());
        return text.substring(0, cut) + ELLIPSIS;
    }
}
