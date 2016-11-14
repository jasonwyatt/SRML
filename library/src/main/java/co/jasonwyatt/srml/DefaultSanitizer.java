package co.jasonwyatt.srml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jason
 *
 * Sanitizes parameters for parameterized strings, and unsanitizes them later.
 */
public class DefaultSanitizer implements Sanitizer {
    private static final Pattern SANITIZE_PATTERN = Pattern.compile("\\{{2}");
    private static final Pattern DESANITIZE_PATTERN = Pattern.compile("\u0000{2}");
    private static final String SANITIZE_REPLACEMENT = "\u0000\u0000";
    private static final String DESANITIZE_REPLACEMENT = "{{";

    public DefaultSanitizer() {
        //
    }

    @Override
    public Object[] sanitizeArgs(Object[] formatArgs) {
        if (formatArgs == null) {
            return null;
        }
        for (int i = 0; i < formatArgs.length; i++) {
            if (formatArgs[i] instanceof CharSequence) {
                Matcher m = SANITIZE_PATTERN.matcher((CharSequence) formatArgs[i]);
                formatArgs[i] = m.replaceAll(SANITIZE_REPLACEMENT);
            }
        }
        return formatArgs;
    }

    @Override
    public String desantitize(String s) {
        Matcher m = DESANITIZE_PATTERN.matcher(s);
        return m.replaceAll(DESANITIZE_REPLACEMENT);
    }
}
