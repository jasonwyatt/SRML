package co.jasonwyatt.srml.tags;

import android.text.Spannable;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jason
 *
 * Allows you to color a chunk of text.
 *
 * Usage: {{color fg=#FF00FF}}my text{{/color}} -> Magenta "my text"
 */

class Color extends ParameterizedTag {
    static final String NAME = "color";
    private static final String PARAM_COLOR_FG = "fg";
    private static final String PARAM_COLOR_BG = "bg";
    private static final Pattern COLOR_VALUE_PATTERN = Pattern.compile("^#([0-9a-f]{3,8})$", Pattern.CASE_INSENSITIVE);

    public Color(String tagStr, int taggedTextStart) {
        super(tagStr, taggedTextStart);
    }

    @Override
    public boolean matchesClosingTag(String tagName) {
        return NAME.equalsIgnoreCase(tagName);
    }

    @Override
    public void operate(Spannable builder, int taggedTextEnd) {
        String colorValue = getParam(PARAM_COLOR_FG);
        if (colorValue != null) {
            int colorInt = getColorInt(colorValue);
            builder.setSpan(new ForegroundColorSpan(colorInt), getTaggedTextStart(), taggedTextEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        colorValue = getParam(PARAM_COLOR_BG);
        if (colorValue != null) {
            int colorInt = getColorInt(colorValue);
            builder.setSpan(new BackgroundColorSpan(colorInt), getTaggedTextStart(), taggedTextEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * Parses a color value hex string into its integer representation.
     * @return Color int value, or -1 if it could not be parsed.
     */
    static int getColorInt(String colorValue) {
        Matcher m = COLOR_VALUE_PATTERN.matcher(colorValue);
        if (!m.find()) {
            throw new BadParameterException("bad color value: "+colorValue);
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
}
