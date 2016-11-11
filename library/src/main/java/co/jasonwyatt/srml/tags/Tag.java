package co.jasonwyatt.srml.tags;

import android.text.Spannable;

/**
 *
 * Created by jason on 11/1/16.
 */
public abstract class Tag {
    private final String mTagStr;
    private final int mTaggedTextStart;

    /**
     *
     * @param tagStr The tag's contents (name and parameters).
     */
    public Tag(String tagStr, int taggedTextStart) {
        mTagStr = tagStr;
        mTaggedTextStart = taggedTextStart;
    }

    public String getTagStr() {
        return mTagStr;
    }

    public int getTaggedTextStart() {
        return mTaggedTextStart;
    }

    public abstract boolean matchesClosingTag(String tagName);
    public abstract void operate(Spannable builder, int taggedTextEnd);
}
