package co.jasonwyatt.srml;

/**
 * Created by jason on 11/10/16.
 *
 * Sanitizes parameters for parameterized strings, and unsanitizes them later.
 */
public class DefaultSanitizer implements Sanitizer {
    public DefaultSanitizer() {
        //
    }

    @Override
    public Object[] sanitizeArgs(Object[] formatArgs) {
        if (formatArgs == null) {
            return null;
        }
        Object[] result = new Object[formatArgs.length];
        for (int i = 0; i < formatArgs.length; i++) {
            if (formatArgs[i] instanceof String) {
                result[i] = ((String)formatArgs[i]).replaceAll("\\{\\{", "\u0000\u0000");
            } else {
                result[i] = formatArgs[i];
            }
        }
        return result;
    }

    @Override
    public String desantitize(String s) {
        return s.replaceAll("\u0000\u0000", "{{");
    }
}
