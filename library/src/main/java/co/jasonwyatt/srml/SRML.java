package co.jasonwyatt.srml;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;

/**
 *
 * Created by jason on 10/31/16.
 */
public final class SRML {
    private static final Transformer defaultTransformer = new DefaultTransformer();

    private SRML() {
        // no outside instantiation needed plz
    }

    public static CharSequence getString(Context context, @StringRes int resId) {
        return getString(context, defaultTransformer, resId);
    }

    public static CharSequence getString(Context context, @StringRes int resId, Object... formatArgs) {
        return getString(context, defaultTransformer, resId, formatArgs);
    }

    public static CharSequence[] getStringArray(Context context, @ArrayRes int resId) {
        return getStringArray(context, defaultTransformer, resId);
    }

    public static CharSequence getQuantityString(Context context, @PluralsRes int resId, int quantity) {
        return getQuantityString(context, defaultTransformer, resId, quantity);
    }

    public static CharSequence getQuantityString(Context context, @PluralsRes int resId, int quantity, Object... formatArgs) {
        return getQuantityString(context, defaultTransformer, resId, quantity, formatArgs);
    }

    public static CharSequence getString(Context context, Transformer transformer, @StringRes int resId) {
        return transformer.transform(context, context.getString(resId));
    }

    public static CharSequence getString(Context context, Transformer transformer, @StringRes int resId, Object... formatArgs) {
        return transformer.transform(context, context.getString(resId, formatArgs));
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
        return transformer.transform(context, context.getResources().getQuantityString(resId, quantity, formatArgs));
    }
}
