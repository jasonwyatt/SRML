package co.jasonwyatt.srml;

import android.content.Context;
import android.text.SpannableStringBuilder;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.jasonwyatt.srml.tags.Tag;
import co.jasonwyatt.srml.tags.TagFactory;

/**
 * The default implementation of {@link Transformer} for SRML.
 * Created by jason on 10/31/16.
 */
public class DefaultTransformer implements Transformer {

    private static final Pattern TAG_PATTERN = Pattern.compile("\\{\\{(/)?(([-a-zA-Z]+)(\\s+[^\\}]+)?)\\}\\}", Pattern.CASE_INSENSITIVE);
    private final Sanitizer mSanitizer;
    private final TagFactory mTagFactory;

    public DefaultTransformer() {
        mSanitizer = new DefaultSanitizer();
        mTagFactory = new TagFactory();
    }

    @Override
    public CharSequence transform(Context context, String srmlString) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        Matcher m = TAG_PATTERN.matcher(srmlString);
        Stack<Tag> tags = new Stack<>();
        int lastEnd = 0;
        while (m.find()) {
            builder.append(mSanitizer.desantitize(srmlString.substring(lastEnd, m.start())));

            String tagDetails = m.group(2);
            String tagName = m.group(3);
            if (m.group(1) != null) {
                // it's a closing tag.
                Tag stackTop = tags.isEmpty() ? null : tags.peek();
                if (stackTop != null && stackTop.matchesClosingTag(tagName)) {
                    stackTop.operate(builder, builder.length());
                    tags.pop();
                }
            } else {
                // it's a new opening tag.
                tags.push(mTagFactory.getTag(tagName, tagDetails, builder.length()));
            }

            lastEnd = m.end();
        }
        // get the rest of the string..
        builder.append(mSanitizer.desantitize(srmlString.substring(lastEnd, srmlString.length())));
        // clean out any remaining tags...
        while (!tags.isEmpty()) {
            Tag t = tags.pop();
            t.operate(builder, builder.length());
        }

        return builder;
    }

    @Override
    public Sanitizer getSanitizer() {
        return mSanitizer;
    }

    @Override
    public TagFactory getTagFactory() {
        return mTagFactory;
    }
}
