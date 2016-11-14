package co.jasonwyatt.srml;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jason
 *
 * General utilities.
 */
public class Utils {
    private static final Pattern SIZE_PATTERN = Pattern.compile("([0-9]+(\\.[0-9]+)?)(sp|dp|px)?", Pattern.CASE_INSENSITIVE);
    private static final Map<String, Integer> sIdentifierCache = new HashMap<>(100);
    private static SRMLImageLoader sImageLoader;

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

    /**
     * Get the integer value for an identifier string like "R.drawable.app_icon".  This method will
     * cache identifier values so later lookups are quick.
     *
     * @param context The context in which to search for the identifier.
     * @param identifierStr The identifier string.
     * @return The actual identifier value.
     */
    public static int getIdentifier(Context context, String identifierStr) {
        if (sIdentifierCache.containsKey(identifierStr)) {
            return sIdentifierCache.get(identifierStr);
        }
        String[] parts = identifierStr.split("\\.");
        int identifier = context.getResources().getIdentifier(parts[2], parts[1], context.getPackageName());
        sIdentifierCache.put(identifierStr, identifier);
        return identifier;
    }

    /**
     * Set the {@link SRMLImageLoader} to use when {@link #loadImage(Context, String, int, int)} is called.
     * @param imageLoader A new image loader.
     */
    public static void setImageLoader(SRMLImageLoader imageLoader) {
        sImageLoader = imageLoader;
    }

    /**
     * Load a bitmap from a url.
     * @param context The current context.
     * @param url The url of the image to load.
     * @param width Requested width of the image.
     * @param height Requested height of the image.
     * @return The loaded image, or null if no {@link SRMLImageLoader} is associated via
     *      {@link #setImageLoader(SRMLImageLoader)}
     */
    public static Bitmap loadImage(Context context, String url, int width, int height) {
        if (sImageLoader == null) {
            throw new IllegalStateException("No SRMLImageLoader specified, please pass one to SRML.setImageLoader().");
        }

        if (width >= 0 && height >= 0) {
            return sImageLoader.loadImage(context, url, width, height);
        }
        return sImageLoader.loadImage(context, url);
    }

    /**
     * Transform the provided size string into a pixel size.<br/>
     * Supported formats:
     * <ul>
     *   <li>"32dp"</li>
     *   <li>"32sp"</li>
     *   <li>"32px"</li>
     *   <li>"32" (interpreted as pixels)</li>
     * </ul>
     * @param context The current context.
     * @param size String representation of the size.
     * @return The resolved size.
     */
    public static int getPixelSize(Context context, String size) {
        if (size == null) {
            return -1;
        }
        Matcher m = SIZE_PATTERN.matcher(size);
        if (m.find()) {
            float value = Float.parseFloat(m.group(1));
            String unit = m.group(3);
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            if (unit == null) {
                return (int) value;
            } else if ("dp".equalsIgnoreCase(unit)) {
                return (int) (value * dm.density);
            } else if ("sp".equalsIgnoreCase(unit)) {
                return (int) (value * dm.scaledDensity);
            } else {
                return (int) value;
            }
        }
        return 0;
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
