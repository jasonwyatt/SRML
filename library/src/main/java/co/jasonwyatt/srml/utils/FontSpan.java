package co.jasonwyatt.srml.utils;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.MetricAffectingSpan;


/**
 * This class is a powerful Span implementation which allows for configuring several aspects of the
 * text to display. It should be instantiated using a {@link Builder}.
 *
 * @author jason
 */
@SuppressLint("ParcelCreator")
public class FontSpan extends MetricAffectingSpan {
    public static final int FLAG_BOLD = 1;
    public static final int FLAG_ITALIC = 2;
    public static final int FLAG_UNDERLINE = 4;
    public static final int FLAG_STRIKETHROUGH = 8;
    public static final int FLAG_SUPERSCRIPT = 16;
    public static final int FLAG_SUBSCRIPT = 32;
    private final int mFlags;
    private final float mSpacing;
    private final int mLineHeight;
    private final int mForegroundColor;
    private final boolean mHasForegroundColor;
    private final int mBackgroundColor;
    private final boolean mHasBackgroundColor;
    private final int mTextSize;
    private final String mTypeface;

    private FontSpan(Builder builder) {
        mSpacing = builder.mSpacing;
        mLineHeight = builder.mLineHeight;
        mFlags = builder.mFlags;
        mForegroundColor = builder.mForegroundColor;
        mHasForegroundColor = builder.mHasForegroundColor;
        mBackgroundColor = builder.mBackgroundColor;
        mHasBackgroundColor = builder.mHasBackgroundColor;
        mTextSize = builder.mTextSize;
        mTypeface = builder.mTypeface;
    }

    @Override
    public void updateMeasureState(TextPaint p) {
        apply(this, p);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        apply(this, ds);
    }

    private static void apply(FontSpan span, TextPaint ds) {
        if (!TextUtils.isEmpty(span.mTypeface)) {
            int oldStyle;
            Typeface old = ds.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }

            Typeface tf = Typeface.create(span.mTypeface, oldStyle);
            int fake = oldStyle & ~tf.getStyle();

            if ((fake & Typeface.BOLD) != 0) {
                ds.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                ds.setTextSkewX(-0.25f);
            }

            ds.setTypeface(tf);
        }

        if (span.mTextSize > 0) {
            ds.setTextSize(span.mTextSize);
        }

        if ((span.mFlags & FLAG_UNDERLINE) != 0) {
            ds.setUnderlineText(true);
        }
        if ((span.mFlags & FLAG_STRIKETHROUGH) != 0) {
            ds.setStrikeThruText(true);
        }
        if ((span.mFlags & FLAG_BOLD) != 0) {
            ds.setFakeBoldText(true);
        }
        if ((span.mFlags & FLAG_ITALIC) != 0) {
            ds.setTextSkewX(-0.25f);
        }
        if ((span.mFlags & FLAG_SUBSCRIPT) != 0) {
            ds.baselineShift -= (int) (ds.ascent() / 2);
        } else if ((span.mFlags & FLAG_SUPERSCRIPT) != 0) {
            ds.baselineShift += (int) (ds.ascent() / 2);
        }

        if (span.mSpacing >= 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ds.setLetterSpacing(span.mSpacing);
        }
        if (span.mLineHeight >= 0) {
            ds.baselineShift += (ds.getTextSize() - span.mLineHeight);
        }

        if (span.mHasForegroundColor) {
            ds.setColor(span.mForegroundColor);
        }
        if (span.mHasBackgroundColor) {
            ds.bgColor = span.mBackgroundColor;
        }
    }

    /**
     * Builder for {@link FontSpan}. Allows you to configure several attributes pertaining to the
     * style of text that is rendered.
     */
    public static class Builder {
        private int mFlags = 0;
        private float mSpacing = -1;
        private int mLineHeight = -1;
        private int mForegroundColor = 0;
        private int mBackgroundColor = 0;
        private boolean mHasForegroundColor = false;
        private boolean mHasBackgroundColor = false;
        private int mTextSize = -1;
        private String mTypeface;

        public Builder() {

        }

        /**
         * Set the character spacing for the FontSpan. Zero is the default, values are ratios of ems.
         * <p><b>NOTE:</b> This is only supported on Lollipop or higher.</p>
         * @param spacing Spacing.
         * @return The builder.
         */
        public Builder spacing(float spacing) {
            mSpacing = spacing;
            return this;
        }

        /**
         * Set the line height for the span (in pixels).
         * @param lineHeightPx New line height.
         * @return The builder.
         */
        public Builder lineHeight(int lineHeightPx) {
            mLineHeight = lineHeightPx;
            return this;
        }

        /**
         * Set the flags for the FontSpan. Can be a bitwise or of any of the following values:
         * <ul>
         *     <li>{@link #FLAG_BOLD}</li>
         *     <li>{@link #FLAG_ITALIC}</li>
         *     <li>{@link #FLAG_UNDERLINE}</li>
         *     <li>{@link #FLAG_STRIKETHROUGH}</li>
         *     <li>{@link #FLAG_SUPERSCRIPT}*</li>
         *     <li>{@link #FLAG_SUBSCRIPT}*</li>
         * </ul>
         * <p>* - Only one of {@link #FLAG_SUBSCRIPT} or {@link #FLAG_SUPERSCRIPT} may be used.</p>
         * @param flags Flags to set.
         * @return The builder.
         */
        public Builder flags(int flags) {
            mFlags = flags;
            return this;
        }

        /**
         * Sets the text foreground color.
         * @param foregroundColor The foreground color for the text.
         * @return The builder.
         */
        public Builder foregroundColor(@ColorInt int foregroundColor) {
            mForegroundColor = foregroundColor;
            mHasForegroundColor = true;
            return this;
        }

        /**
         * Sets the text background color.
         * @param backgroundColor The background color for the text.
         * @return The builder.
         */
        public Builder backgroundColor(@ColorInt int backgroundColor) {
            mBackgroundColor = backgroundColor;
            mHasBackgroundColor = true;
            return this;
        }

        /**
         * Sets the text size.
         * @param sizePixels The text size (in pixels)
         * @return The builder.
         */
        public Builder textSize(int sizePixels) {
            mTextSize = sizePixels;
            return this;
        }

        /**
         * Sets the typeface string.
         * @param typeface New typeface identifier string.
         * @return The builder.
         */
        public Builder typeface(String typeface) {
            mTypeface = typeface;
            return this;
        }

        /**
         * Construct a FontSpan from the builder.
         * @return A new {@link FontSpan}
         */
        public FontSpan build() {
            return new FontSpan(this);
        }
    }
}
