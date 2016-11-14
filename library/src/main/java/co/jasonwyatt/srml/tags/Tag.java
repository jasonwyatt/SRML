package co.jasonwyatt.srml.tags;

import android.text.Spannable;

/**
 * Created by jason on 11/1/16.
 *
 * Tags are the meat and potatoes of SRML. Everything you can use to mark up your string resources
 * extends from this class.  If you wish to create your own tags you can extend this directly. If
 * you prefer to make a custom tag which accepts parameters, extend {@link ParameterizedTag} instead
 * and it'll do the heavy lifting for you regarding parsing.
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
     * Return whether or not the closing tag seen matches this tag.
     *
     * <b>NOTE:</b> Typically you only need to check {@param tagName} against the name you use for the tag.
     * @param tagName The name of the closing tag.
     * @return Whether or not that name matches this tag, if so, SRML will call
     *         {@link #operate(Spannable, int)}, passing the ending index where the closing tag was
     *         found.
     */
    public abstract boolean matchesClosingTag(String tagName);

    /**
     * Operate on the provided {@link Spannable}. You'll typically want to call
     * {@link Spannable#setSpan(Object, int, int, int)} on it, using {@link #getTaggedTextStart()}
     * and {@param taggedTextEnd} as the start and end parameters.
     *
     * @param spannable The {@link Spannable} on which to operate.
     * @param taggedTextEnd The index in the final (parsed) string where the closing tag was seen.
     */
    public abstract void operate(Spannable spannable, int taggedTextEnd);
}
