package co.jasonwyatt.srml.tags;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

/**
 * @author jason
 *
 * Tag for underlining text.
 */
class Underline extends Tag {
    static final String NAME = "u";

    Underline(String tagStr, int taggedTextStart) {
        super(tagStr, taggedTextStart);
    }

    @Override
    public boolean matchesClosingTag(String tagName) {
        return NAME.equalsIgnoreCase(tagName);
    }

    @Override
    public void operate(Spannable builder, int taggedTextEnd) {
        builder.setSpan(new UnderlineSpan(), getTaggedTextStart(), taggedTextEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }
}
