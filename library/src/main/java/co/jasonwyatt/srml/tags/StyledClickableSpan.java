package co.jasonwyatt.srml.tags;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;

import co.jasonwyatt.srml.utils.Utils;

/**
 * Extension of {@link ClickableSpan} which allows the user to customize its appearance, rather than
 * simply always using linkColor and underlining.
 *
 * @author jason
 */
public abstract class StyledClickableSpan extends ClickableSpan {
    private static final String PARAM_COLOR = "color";
    private static final String PARAM_UNDERLINE = "underline";

    private boolean mUseDefaultColor = true;
    private int mColor = 0;
    private boolean mUnderlined = true;

    /**
     * Parse the COLOR and UNDERLINE params from the given parameterized tag and use them to
     * determine how to render.
     * @param context Current context (for color lookup)
     * @param tag The tag using this StyledClickableSpan
     */
    protected void useParams(Context context, ParameterizedTag tag) {
        String colorValue = tag.getParam(PARAM_COLOR);
        String underline = tag.getParam(PARAM_UNDERLINE);

        if (colorValue != null && underline != null) {
            setColorAndUnderlined(Utils.getColorInt(context, colorValue), !"false".equalsIgnoreCase(underline));
        } else if (colorValue != null) {
            setColorOnly(Utils.getColorInt(context, colorValue));
        } else if (underline != null) {
            setUnderlinedOnly(!"false".equalsIgnoreCase(underline));
        }
    }

    private void setDefaultStyle() {
        mUseDefaultColor = true;
        mColor = 0;
        mUnderlined = true;
    }

    private void setColorOnly(int color) {
        mUseDefaultColor = false;
        mColor = color;
        mUnderlined = true;
    }

    private void setUnderlinedOnly(boolean underlined) {
        mUseDefaultColor = true;
        mColor = 0;
        mUnderlined = underlined;
    }

    private void setColorAndUnderlined(int color, boolean underlined) {
        mUseDefaultColor = false;
        mColor = color;
        mUnderlined = underlined;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(mUseDefaultColor ? ds.linkColor : mColor);
        ds.setUnderlineText(mUnderlined);
    }
}
