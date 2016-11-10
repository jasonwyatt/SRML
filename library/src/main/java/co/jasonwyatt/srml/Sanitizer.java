package co.jasonwyatt.srml;

/**
 * Created by jason on 11/10/16.
 */
public interface Sanitizer {
    /**
     * Sanitize the string arguments in formatArgs by replacing instances of our tag-start borders
     * with equal-length replacements, so they don't get picked up by the transformer.
     */
    Object[] sanitizeArgs(Object[] formatArgs);

    /**
     * Desanitize a chunk of text.
     */
    String desantitize(String s);
}
