package co.jasonwyatt.srml.tags;

import android.content.Context;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.SuperscriptSpan;

/**
 * Wrapped-text becomes superscriptized..
 * @author jason
 */

class Superscript extends Tag {
    static final String NAME = "sup";

    public Superscript(String tagStr, int taggedTextStart) {
        super(tagStr, taggedTextStart);
    }

    @Override
    public boolean matchesClosingTag(String tagName) {
        return NAME.equalsIgnoreCase(tagName);
    }

    @Override
    public void operate(Context context, Spannable spannable, int taggedTextEnd) {
        spannable.setSpan(new SuperscriptSpan(), getTaggedTextStart(), taggedTextEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }
}
