package co.jasonwyatt.srml.tags;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;

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
    public void operate(Context context, Spannable builder, int taggedTextEnd) {
        String url = getParam(URL_PARAM_NAME);
        LinkSpan span = new LinkSpan(url);
        span.useParams(context, this);
        builder.setSpan(span, getTaggedTextStart(), taggedTextEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private static class LinkSpan extends StyledClickableSpan {
        private final String mUrl;

        private LinkSpan(String url) {
            mUrl = url;
        }

        @Override
        public void onClick(View widget) {
            Uri uri = Uri.parse(mUrl);
            Context context = widget.getContext();
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.w("LinkSpan", "Actvity was not found for intent, " + intent.toString());
            }
        }
    }
}
