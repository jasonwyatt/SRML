package co.jasonwyatt.srml.tags;

import android.content.Context;
import android.text.Spannable;

import co.jasonwyatt.srml.Transformer;

/**
 * Tags are the meat and potatoes of SRML. Everything you can use to mark up your string resources
 * extends from this class.  If you wish to create your own tags you can extend this directly. If
 * you prefer to make a custom tag which accepts parameters, extend {@link ParameterizedTag} instead
 * and it'll do the heavy lifting for you regarding parsing.
 *
 * @author jason
 */
public abstract class Tag {
    private final String mTagStr;
    private final int mTaggedTextStart;

    /**
     * Create a new instance of {@link Tag}
     * @param tagStr The tag's contents (name and parameters).
     */
    public Tag(String tagStr, int taggedTextStart) {
        mTagStr = tagStr;
        mTaggedTextStart = taggedTextStart;
    }

    /**
     * Get the whole contents of the opening tag which instigated creation of this {@link Tag}
     * instance.
     * @return The whole tag string.
     */
    public String getTagStr() {
        return mTagStr;
    }

    /**
     * Get the starting character index (of the parsed string) of this tag.
     * @return Starting index.
     */
    public int getTaggedTextStart() {
        return mTaggedTextStart;
    }

    /**
     * Whether or not this tag is allowed to be an empty, self-closing tag. <br/><br/>
     * <b>NOTE:</b> If you return <code>true</code>, the {@link Transformer} will call
     * {@link #getRequiredSpacesWhenEmpty()} before it calls {@link #operate(Context, Spannable, int)}
     *
     * @return Whether the tag can be empty.
     */
    public boolean canBeEmpty() {
        return false;
    }

    /**
     * When <code>true</code> is returned from {@link #canBeEmpty()}, this method will be called to
     * allow you to specify a number of non-breaking spaces required by the Tag. When
     * {@link #operate(Context, Spannable, int)} is called, it will be passed the value from
     * {@link #getTaggedTextStart()} plus the return value from this method.
     *
     * @return The number of required non-breaking spaces for the tag.
     */
    public int getRequiredSpacesWhenEmpty() {
        return 0;
    }

    /**
     * Return whether or not the closing tag seen matches this tag.<br/><br/>
     *
     * <b>NOTE:</b> Typically you only need to check {@param tagName} against the name you use for the tag.
     * @param tagName The name of the closing tag.
     * @return Whether or not that name matches this tag, if so, SRML will call
     *         {@link #operate(Context, Spannable, int)}, passing the ending index where the closing tag was
     *         found.
     */
    public abstract boolean matchesClosingTag(String tagName);

    /**
     * Operate on the provided {@link Spannable}.<br/><br/>
     *
     * You'll typically want to call {@link Spannable#setSpan(Object, int, int, int)} on it,
     * using {@link #getTaggedTextStart()} and {@param taggedTextEnd} as the start and end
     * parameters.
     *
     * @param context The context for the resource.
     * @param spannable The {@link Spannable} on which to operate.
     * @param taggedTextEnd The index in the final (parsed) string where the closing tag was seen.
     */
    public abstract void operate(Context context, Spannable spannable, int taggedTextEnd);
}
