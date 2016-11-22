package co.jasonwyatt.srml.tags;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;

import java.util.regex.Pattern;

import co.jasonwyatt.srml.utils.FontSpan;
import co.jasonwyatt.srml.utils.Utils;

/**
 * A powerful tag to configure several attributes of the font.
 * @author jason
 */
class FontTag extends ParameterizedTag {
    static final String NAME = "font";
    private static final String PARAM_COLOR_FG = "fg";
    private static final String PARAM_COLOR_BG = "bg";
    private static final String PARAM_TYPEFACE = "typeface";
    private static final String PARAM_STYLE = "style";
    private static final String PARAM_LETTER_SPACING = "letter_spacing";
    private static final String PARAM_LINE_HEIGHT = "line_height";
    private static final String PARAM_TEXT_APPEARANCE = "text_appearance";
    private static final String PARAM_TEXT_SIZE = "size";
    private static final Pattern STYLE_DIVIDER = Pattern.compile("\\|");
    private static final String STYLE_UNDERLINE = "underline";
    private static final String STYLE_BOLD = "bold";
    private static final String STYLE_ITALIC = "italic";
    private static final String STYLE_STRIKETHROUGH = "strike";
    private static final String STYLE_SUPERSCRIPT = "super";
    private static final String STYLE_SUBSCRIPT = "sub";
    private int mStart;
    private int mEnd;

    FontTag(String tagStr, int taggedTextStart) {
        super(tagStr, taggedTextStart);
    }

    @Override
    public boolean matchesClosingTag(String tagName) {
        return NAME.equalsIgnoreCase(tagName);
    }

    @Override
    public void operate(Context context, Spannable spannable, int taggedTextEnd) {
        mStart = getTaggedTextStart();
        mEnd = taggedTextEnd;

        String textAppearanceValue = getParam(PARAM_TEXT_APPEARANCE);
        if (!TextUtils.isEmpty(textAppearanceValue)) {
            applySpan(spannable, new TextAppearanceSpan(context, Utils.getIdentifier(context, textAppearanceValue)));
        }

        FontSpan.Builder span = new FontSpan.Builder();

        String letterSpacingValue = getParam(PARAM_LETTER_SPACING);
        float letterSpacing;
        if (!TextUtils.isEmpty(letterSpacingValue)) {
            try {
                letterSpacing = Float.parseFloat(letterSpacingValue);
            } catch (NumberFormatException nfe) {
                throw new BadParameterException("Invalid letter_spacing value:"+letterSpacingValue, nfe);
            }
            if (letterSpacing < 0) {
                throw new BadParameterException("letter_spacing cannot be less than 0: "+getTagStr());
            }
            span.spacing(letterSpacing);
        }

        String lineHeightValue = getParam(PARAM_LINE_HEIGHT);
        if (!TextUtils.isEmpty(lineHeightValue)) {
            span.lineHeight(Utils.getPixelSize(context, lineHeightValue));
        }

        span.flags(getStyles(getParam(PARAM_STYLE)));


        String fgValue = getParam(PARAM_COLOR_FG);
        if (!TextUtils.isEmpty(fgValue)) {
            span.foregroundColor(Utils.getColorInt(context, fgValue));
        }

        String bgValue = getParam(PARAM_COLOR_BG);
        if (!TextUtils.isEmpty(bgValue)) {
            span.backgroundColor(Utils.getColorInt(context, bgValue));
        }

        String typefaceValue = getParam(PARAM_TYPEFACE);
        if (!TextUtils.isEmpty(typefaceValue)) {
            span.typeface(typefaceValue);
        }

        String sizeValue = getParam(PARAM_TEXT_SIZE);
        if (!TextUtils.isEmpty(sizeValue)) {
            span.textSize(Utils.getPixelSize(context, sizeValue));
        }

        applySpan(spannable, span.build());

    }

    private int getStyles(@Nullable String styleString) {
        int styleSpanValue = 0;
        if (TextUtils.isEmpty(styleString)) {
            return styleSpanValue;
        }

        String[] styles = STYLE_DIVIDER.split(styleString);
        boolean seenSuperSub = false;
        for (String style : styles) {
            switch (style) {
                case STYLE_BOLD:
                    styleSpanValue |= FontSpan.FLAG_BOLD;
                    break;
                case STYLE_ITALIC:
                    styleSpanValue |= FontSpan.FLAG_ITALIC;
                    break;
                case STYLE_UNDERLINE:
                    styleSpanValue |= FontSpan.FLAG_UNDERLINE;
                    break;
                case STYLE_STRIKETHROUGH:
                    styleSpanValue |= FontSpan.FLAG_STRIKETHROUGH;
                    break;
                case STYLE_SUPERSCRIPT:
                    if (seenSuperSub) {
                        throw new BadParameterException("\"super\" style requested, but \"sub\" or \"super\" was already in the {{font}} style: "+styleString);
                    }
                    styleSpanValue |= FontSpan.FLAG_SUPERSCRIPT;
                    seenSuperSub = true;
                    break;
                case STYLE_SUBSCRIPT:
                    if (seenSuperSub) {
                        throw new BadParameterException("\"sub\" style requested, but \"sub\" or \"super\" was already in the {{font}} style: "+styleString);
                    }
                    styleSpanValue |= FontSpan.FLAG_SUBSCRIPT;
                    seenSuperSub = true;
                    break;
                default:
                    throw new BadParameterException("Invalid value for {{font}} style: "+style+" in "+styleString);
            }
        }

        return styleSpanValue;
    }

    private void applySpan(Spannable s, Object o) {
        s.setSpan(o, mStart, mEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

}
