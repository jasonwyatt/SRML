package co.jasonwyatt.srml.utils;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Simple wrapper of {@link String} which the {@link co.jasonwyatt.srml.Sanitizer} should look for,
 * and skip the escape process.
 *
 * @author jason
 */

public class SafeString implements CharSequence, Serializable {
    private final String mString;

    public SafeString(@NonNull String s) {
        mString = s;
    }

    public boolean isEmpty() {
        return mString.isEmpty();
    }

    /**
     * Returns the length of this SafeString.  The length is the number
     * of 16-bit <code>char</code>s in the sequence.</p>
     *
     * @return  the number of <code>char</code>s in this sequence
     */
    @Override
    public int length() {
        return mString.length();
    }

    /**
     * Returns the <code>char</code> value at the specified index.  An index ranges from zero
     * to <tt>length() - 1</tt>.  The first <code>char</code> value of the sequence is at
     * index zero, the next at index one, and so on, as for array
     * indexing. </p>
     *
     * <p>If the <code>char</code> value specified by the index is a
     * <a href="{@docRoot}/java/lang/Character.html#unicode">surrogate</a>, the surrogate
     * value is returned.
     *
     * @param   index   the index of the <code>char</code> value to be returned
     *
     * @return  the specified <code>char</code> value
     *
     * @throws  IndexOutOfBoundsException
     *          if the <tt>index</tt> argument is negative or not less than
     *          <tt>length()</tt>
     */
    @Override
    public char charAt(int index) {
        return mString.charAt(index);
    }

    /**
     * Returns a new <code>CharSequence</code> that is a subsequence of this sequence.
     * The subsequence starts with the <code>char</code> value at the specified index and
     * ends with the <code>char</code> value at index <tt>end - 1</tt>.  The length
     * (in <code>char</code>s) of the
     * returned sequence is <tt>end - start</tt>, so if <tt>start == end</tt>
     * then an empty sequence is returned. </p>
     *
     * @param   start   the start index, inclusive
     * @param   end     the end index, exclusive
     *
     * @return  the specified subsequence
     *
     * @throws  IndexOutOfBoundsException
     *          if <tt>start</tt> or <tt>end</tt> are negative,
     *          if <tt>end</tt> is greater than <tt>length()</tt>,
     *          or if <tt>start</tt> is greater than <tt>end</tt>
     */
    @Override
    public CharSequence subSequence(int start, int end) {
        return mString.subSequence(start, end);
    }

    /**
     * Returns a string containing the characters in this sequence in the same
     * order as this sequence.  The length of the string will be the length of
     * this sequence. </p>
     *
     * @return  a string consisting of exactly this sequence of characters
     */
    @Override
    public String toString() {
        return mString;
    }

    /**
     * Whether or not the provided object matches this SafeString.
     * @param obj Other object.
     * @return Whether or not the other object is a SafeString and its inner string value matches
     *         ours.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof SafeString)) {
            return false;
        }
        SafeString other = (SafeString) obj;
        return other.mString.equals(mString);
    }

    /**
     * Get the hashCode for this SafeString.
     * @return The hashcode for the inner {@link String} value.
     */
    @Override
    public int hashCode() {
        return mString.hashCode();
    }
}
