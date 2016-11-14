package co.jasonwyatt.srml;

import android.content.Context;
import android.text.SpannableStringBuilder;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.jasonwyatt.srml.tags.BadTagException;
import co.jasonwyatt.srml.tags.Tag;
import co.jasonwyatt.srml.tags.TagFactory;

/**
 * The default implementation of {@link Transformer} for SRML.
 * @author jason
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
            boolean isSelfClosed = tagDetails.endsWith("/");
            if (m.group(1) != null) {
                // it's a closing tag.
                Tag stackTop = tags.isEmpty() ? null : tags.peek();
                if (stackTop != null && stackTop.matchesClosingTag(tagName)) {
                    stackTop.operate(context, builder, builder.length());
                    tags.pop();
                }
            } else if (isSelfClosed) {
                Tag tag = mTagFactory.getTag(tagName, tagDetails.substring(0, tagDetails.length()-1), builder.length());
                if (tag.canBeEmpty()) {
                    appendNonbreaking(builder, tag.getRequiredSpacesWhenEmpty());
                    tag.operate(context, builder, builder.length());
                } else {
                    throw new BadTagException("Tag "+m.group(0)+" is not allowed to be self-closing.");
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
            t.operate(context, builder, builder.length());
        }

        return builder;
    }

    void appendNonbreaking(SpannableStringBuilder builder, int spaces) {
        for (int i = 0; i < spaces; i++) {
            builder.append("\u00A0");
        }
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
