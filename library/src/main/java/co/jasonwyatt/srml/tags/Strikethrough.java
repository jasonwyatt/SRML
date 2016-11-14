package co.jasonwyatt.srml.tags;

import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;

/**
 * @author jason
 *
 * For strikethroughs around text.
 */
class Strikethrough extends Tag {
    static final String NAME = "strike";

    public Strikethrough(String tagStr, int taggedTextStart) {
        super(tagStr, taggedTextStart);
    }

    @Override
    public boolean matchesClosingTag(String tagName) {
        return NAME.equalsIgnoreCase(tagName);
    }

    @Override
    public void operate(Spannable builder, int taggedTextEnd) {
        builder.setSpan(new StrikethroughSpan(), getTaggedTextStart(), taggedTextEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }
}
