package co.jasonwyatt.srml;

/**
 * Created by jason on 11/14/16.
 *
 * General utilities.
 */
public class Utils {
    private Utils() {
        // nothing...
    }

    /**
     * Join an array of {@link CharSequence} objects, with a given divider.
     */
    public static String join(CharSequence divider, CharSequence[] parts) {
        // guess at a decent first size for the string builder..
        StringBuilder sb = new StringBuilder(parts.length*6);

        for (CharSequence s : parts) {
            if (sb.length() > 0) {
                sb.append(divider);
            }
            sb.append(s);
        }

        return sb.toString();
    }

    public static class Pair<F, S> {
        public final F first;
        public final S second;

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }
    }
}
