package co.jasonwyatt.srml.tags;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;

import co.jasonwyatt.srml.Utils;

/**
 * The DrawableTag allows you to embed images in string resources.
 *
 * {{drawable res=R.drawable.app_icon /}}
 *
 * This is a self-closable tag that can be empty, so note that you need the " /" before the closing
 * "}}"
 *
 * @author jason
 */
class DrawableTag extends ParameterizedTag {
    static final String NAME = "drawable";
    private static final String PARAM_NAME_RES = "res";
    private static final String PARAM_NAME_URL = "url";
    private static final String PARAM_NAME_WIDTH = "width";
    private static final String PARAM_NAME_HEIGHT = "height";
    private static final String PARAM_NAME_ALIGNMENT = "align";
    private static final String PARAM_VALUE_ALIGN_BASELINE = "baseline";
    private static final String PARAM_VALUE_ALIGN_BOTTOM = "bottom";

    /**
     * {@inheritDoc}
     *
     * @param tagStr
     * @param taggedTextStart
     */
    public DrawableTag(String tagStr, int taggedTextStart) {
        super(tagStr, taggedTextStart);
    }

    @Override
    public boolean canBeEmpty() {
        return true;
    }

    @Override
    public int getRequiredSpacesWhenEmpty() {
        return 1;
    }

    @Override
    public boolean matchesClosingTag(String tagName) {
        return NAME.equalsIgnoreCase(tagName);
    }

    @Override
    public void operate(Context context, Spannable spannable, int taggedTextEnd) {
        spannable.setSpan(new ImageSpan(loadDrawable(context), getVerticalAlignment()), getTaggedTextStart(), taggedTextEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private int getVerticalAlignment() {
        String alignmentRaw = getParam(PARAM_NAME_ALIGNMENT);
        if (alignmentRaw == null) {
            return DynamicDrawableSpan.ALIGN_BASELINE;
        }
        switch (alignmentRaw) {
            case PARAM_VALUE_ALIGN_BASELINE:
                return DynamicDrawableSpan.ALIGN_BASELINE;
            case PARAM_VALUE_ALIGN_BOTTOM:
                return DynamicDrawableSpan.ALIGN_BOTTOM;
            default:
                throw new BadParameterException("Invalid alignment param for tag: "+getTagStr());
        }
    }

    private Drawable loadDrawable(Context context) {
        Drawable result = null;
        String resId = getParam(PARAM_NAME_RES);
        String url = getParam(PARAM_NAME_URL);
        int widthPixels = Utils.getPixelSize(context, getParam(PARAM_NAME_WIDTH));
        int heightPixels = Utils.getPixelSize(context, getParam(PARAM_NAME_HEIGHT));

        if (resId != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                result = context.getResources().getDrawable(Utils.getIdentifier(context, resId), context.getTheme());
            } else {
                //noinspection deprecation
                result = context.getResources().getDrawable(Utils.getIdentifier(context, resId));
            }
        } else if (url != null) {
            result = new BitmapDrawable(context.getResources(), Utils.loadImage(context, url, widthPixels, heightPixels));
        }

        if (result == null) {
            throw new BadParameterException("Could not load a drawable for tag: "+getTagStr());
        }
        if (widthPixels > 0 && heightPixels > 0) {
            result.setBounds(0, 0, widthPixels, heightPixels);
        } else if (widthPixels > 0) {
            result.setBounds(0, 0, widthPixels, result.getIntrinsicHeight());
        } else if (heightPixels > 0) {
            result.setBounds(0, 0, result.getIntrinsicWidth(), heightPixels);
        } else {
            result.setBounds(0, 0, result.getIntrinsicWidth(), result.getIntrinsicHeight());
        }
        return result;
    }
}
