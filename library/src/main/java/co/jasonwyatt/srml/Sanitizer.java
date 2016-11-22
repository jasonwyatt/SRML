package co.jasonwyatt.srml;

import android.content.Context;

/**
 * Defines a sanitizer for SRML. Responsible for sanitizing and de-sanitizing formatArgs passed to
 * {@link SRML#getString(Context, int, Object[])} and friends.
 *
 * @author jason
 */
public interface Sanitizer {
    /**
     * Sanitize the string arguments in formatArgs by replacing instances of our tag-start borders
     * with equal-length replacements, so they don't get picked up by the transformer.
     *
     * Args which are an instance of {@link co.jasonwyatt.srml.utils.SafeString} should not be
     * sanitized.
     */
    Object[] sanitizeArgs(Object[] formatArgs);

    /**
     * Desanitize a chunk of text.
     */
    String desantitize(String s);
}
