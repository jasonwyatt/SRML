package co.jasonwyatt.srml;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.jasonwyatt.srml.tags.BadParameterException;

/**
 * @author jason
 *
 * General utilities.
 */
public class Utils {
    private static final Pattern SIZE_PATTERN = Pattern.compile("([0-9]+(\\.[0-9]+)?)(sp|dp|px)?", Pattern.CASE_INSENSITIVE);
    private static final Pattern COLOR_VALUE_PATTERN = Pattern.compile("^#([0-9a-f]+)$", Pattern.CASE_INSENSITIVE);
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

    /**
     * Parses a color value hex string into its integer representation. If it's not a hex string, it
     * tries to interpret {@param colorValue} as a resource identifier.
     *
     * @throws {@link NumberFormatException} if {@param colorValue} was not a hex string.
     * @throws {@link BadParameterException} if the color value could not be parsed.
     * @return Color int value.
     */
    public static int getColorInt(String colorValue) {
        Matcher m = COLOR_VALUE_PATTERN.matcher(colorValue);
        if (!m.find()) {
            throw new NumberFormatException();
        }

        colorValue = m.group(1);
        int colorLength = colorValue.length();

        if (colorLength == 3) {
            int raw = Integer.parseInt(colorValue, 16);
            // 0xFFFFFF
            int first = (raw & 0xF00) >> 8;
            int second = (raw & 0x0F0) >> 4;
            int third = raw & 0x00F;
            return (0xFF << 24) | (first << 20) | (first << 16) | (second << 12) | (second << 8) | (third << 4) | third;
        } else if (colorLength == 6) {
            return (0xFF << 24) | Integer.parseInt(colorValue, 16);
        } else if (colorLength == 8) {
            return (int) Long.parseLong(colorValue, 16);
        }
        throw new BadParameterException("could not parse color value: "+colorValue);
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
