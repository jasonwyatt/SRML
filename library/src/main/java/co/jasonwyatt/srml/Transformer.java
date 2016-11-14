package co.jasonwyatt.srml;

import android.content.Context;

import co.jasonwyatt.srml.tags.TagFactory;

/**
 * Defines an object which is capable of transforming a given SRML-marked-up String into a
 * styled/spanned CharSequence according to the tags used in the string.
 * @author jason
 */
public interface Transformer {
    CharSequence transform(Context context, String srmlString);

    Sanitizer getSanitizer();

    /**
     * Get the {@link TagFactory} for the transformer.
     */
    TagFactory getTagFactory();
}
