package co.jasonwyatt.srml.tags;

import android.content.Context;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.SubscriptSpan;

/**
 * Wrapped-text becomes subscript.
 * @author jason
 */

class Subscript extends Tag {
    static final String NAME = "sub";

    public Subscript(String tagStr, int taggedTextStart) {
        super(tagStr, taggedTextStart);
    }

    @Override
    public boolean matchesClosingTag(String tagName) {
        return NAME.equalsIgnoreCase(tagName);
    }

    @Override
    public void operate(Context context, Spannable spannable, int taggedTextEnd) {
        spannable.setSpan(new SubscriptSpan(), getTaggedTextStart(), taggedTextEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }
}
