package co.jasonwyatt.srml;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;

import co.jasonwyatt.srml.tags.Tag;
import co.jasonwyatt.srml.utils.Utils;

/**
 * SRML stands for <b>S</b>tring <b>R</b>esource <b>M</b>arkup <b>L</b>anguage.
 *
 * @author jason
 */
public final class SRML {
    private static Transformer sTransformer;

    static {
        configure(new DefaultTransformer(), null);
    }

    private SRML() {
        // no outside instantiation needed plz
    }

    /**
     * Supply a {@link Transformer} for SRML.
     * @param transformer The new transformer.
     * @param imageLoader An {@link SRMLImageLoader} for SRML.
     */
    public static void configure(Transformer transformer, SRMLImageLoader imageLoader) {
        sTransformer = transformer;
        setImageLoader(imageLoader);
    }

    /**
     * Set an image loader for SRML.
     * @param imageLoader The new image loader.
     */
    public static void setImageLoader(SRMLImageLoader imageLoader) {
        Utils.setImageLoader(imageLoader);
    }

    /**
     * Register a new {@link Tag} type with SRML.
     * @param name The tag's name, aka: the first part in a tag after the <code>{{</code> or <code>{{/</code>
     * @param tagClass The class for an implementation of {@link Tag}
     */
    public static void registerTag(String name, Class<? extends Tag> tagClass) {
        sTransformer.getTagFactory().registerTag(name, tagClass);
    }

    /**
     * Analog of {@link Context#getString(int)} for SRML.
     * @param context Context to retrieve the string resource from.
     * @param resId String resource.
     * @return The templatized string.
     */
    public static CharSequence getString(Context context, @StringRes int resId) {
        return getString(context, sTransformer, resId);
    }

    /**
     * Analog of {@link Context#getString(int, Object[])} for SRML.
     * @param context Context to retrieve the string resource from.
     * @param resId String resource.
     * @param formatArgs Format arguments for the string, passed along to {@link Context#getString(int, Object[]}
     * @return The templatized string.
     */
    public static CharSequence getString(Context context, @StringRes int resId, Object... formatArgs) {
        return getString(context, sTransformer, resId, formatArgs);
    }

    public static CharSequence[] getStringArray(Context context, @ArrayRes int resId) {
        return getStringArray(context, sTransformer, resId);
    }

    public static CharSequence getQuantityString(Context context, @PluralsRes int resId, int quantity) {
        return getQuantityString(context, sTransformer, resId, quantity);
    }

    public static CharSequence getQuantityString(Context context, @PluralsRes int resId, int quantity, Object... formatArgs) {
        return getQuantityString(context, sTransformer, resId, quantity, formatArgs);
    }

    public static CharSequence getString(Context context, Transformer transformer, @StringRes int resId) {
        return transformer.transform(context, context.getString(resId));
    }

    public static CharSequence getString(Context context, Transformer transformer, @StringRes int resId, Object... formatArgs) {
        return transformer.transform(context, context.getString(resId, transformer.getSanitizer().sanitizeArgs(formatArgs)));
    }

    public static CharSequence[] getStringArray(Context context, Transformer transformer, @ArrayRes int resId) {
        String[] strings = context.getResources().getStringArray(resId);
        CharSequence[] result = new CharSequence[strings.length];
        for (int i = 0, len = result.length; i < len; i++) {
            result[i] = transformer.transform(context, strings[i]);
        }
        return result;
    }

    public static CharSequence getQuantityString(Context context, Transformer transformer, @PluralsRes int resId, int quantity) {
        return transformer.transform(context, context.getResources().getQuantityString(resId, quantity));
    }

    public static CharSequence getQuantityString(Context context, Transformer transformer, @PluralsRes int resId, int quantity, Object... formatArgs) {
        return transformer.transform(context, context.getResources().getQuantityString(resId, quantity, transformer.getSanitizer().sanitizeArgs(formatArgs)));
    }

    /**
     * Mark up a string with SRML.
     * @param context Current context
     * @param str String to mark up.
     * @return Marked-up CharSequence.
     */
    public static CharSequence markup(Context context, String str) {
        return sTransformer.transform(context, str);
    }
}
