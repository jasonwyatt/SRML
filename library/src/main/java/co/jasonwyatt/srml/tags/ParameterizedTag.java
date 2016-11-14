package co.jasonwyatt.srml.tags;

import android.os.Build;
import android.util.ArrayMap;
import android.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.jasonwyatt.srml.Utils;

/**
 * Created by jason on 11/11/16.
 *
 * A ParameterizedTag is one which can contain an arbitrarily long list of parameters, defined as
 * <code>name=value</code> pairs after the tag name in the opening tag.
 *
 * Parameters can be of the following formats:
 * <ul>
 *     <li><code>param_name=param_value</code> if you do not need spaces, or</li>
 *     <li><code>param_name=`param value`</code> if your param does need spaces</li>
 * </ul>
 */
public abstract class ParameterizedTag extends Tag {
    private static final Pattern PARAMS_PATTERN = Pattern.compile("\\s+(([a-zA-Z0-9_\\.]+)=(`((?:[^`]|\\s)*)`|([^\\s}]+)))");
    private final Map<String, String> mParams;

    /**
     * {@inheritDoc}
     */
    public ParameterizedTag(String tagStr, int taggedTextStart) {
        super(tagStr, taggedTextStart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mParams = new ArrayMap<>(3);
        } else {
            mParams = new HashMap<>(3);
        }

        parseParams(tagStr, mParams);
    }

    /**
     * Get a particular parameter from the params list parsed from the original tag string.
     * @param name Name of the parameter.
     */
    protected String getParam(String name) {
        return mParams.get(name);
    }

    /**
     * Get all parameters where the keys match the provided pattern.
     * @param pattern A {@link Pattern} to match against the param names.
     * @return List of {@link Utils.Pair<String, String>} pairs, with the keys & values for the
     *         parameters which match {@param pattern}
     */
    protected List<Utils.Pair<String, String>> getParamsMatching(Pattern pattern) {
        return getParamsMatching(pattern, mParams);
    }

    static void parseParams(String tagStr, Map<String, String> out) {
        Matcher m = PARAMS_PATTERN.matcher(tagStr);
        while (m.find()) {
            String paramName = m.group(2);
            String paramValue = m.group(4);
            if (paramValue == null) {
                paramValue = m.group(5);
            }
            out.put(paramName, paramValue);
        }
    }

    static List<Utils.Pair<String, String>> getParamsMatching(Pattern pattern, Map<String, String> from) {
        List<Utils.Pair<String, String>> result = new LinkedList<>();

        for (Map.Entry<String, String> entry : from.entrySet()) {
            String key = entry.getKey();
            Matcher m = pattern.matcher(key);
            if (m.matches()) {
                result.add(new Utils.Pair<>(key, entry.getValue()));
            }
        }

        return result;
    }
}
