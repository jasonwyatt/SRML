package co.jasonwyatt.srml.tags;

import android.text.Spannable;
import android.text.Spanned;
import android.text.style.URLSpan;

/**
 * @author jason
 *
 * The {{link url=http://.....}} tag creates a clickable link Spannable in the text.
 */
class Link extends ParameterizedTag {
    static final String NAME = "link";
    static final String URL_PARAM_NAME = "url";

    Link(String tagStr, int taggedTextStart) {
        super(tagStr, taggedTextStart);
    }

    @Override
    public boolean matchesClosingTag(String tagName) {
        return NAME.equalsIgnoreCase(tagName);
    }

    @Override
    public void operate(Spannable builder, int taggedTextEnd) {
        String url = getParam(URL_PARAM_NAME);

        builder.setSpan(new URLSpan(url), getTaggedTextStart(), taggedTextEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }
}
