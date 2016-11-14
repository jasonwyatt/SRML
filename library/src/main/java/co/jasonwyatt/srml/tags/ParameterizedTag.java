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
 * name=value pairs after the tag name in the opening tag.
 */
public abstract class ParameterizedTag extends Tag {
    private static final Pattern PARAMS_PATTERN = Pattern.compile("\\s+(([a-zA-Z0-9_\\.]+)=(`((?:[^`]|\\s)*)`|([^\\s}]+)))");
    private final Map<String, String> mParams;

    /**
     * Create a new parameterized tag.
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
     * Get a particular parameter from the params list supplied in the constructor.
     */
    protected String getParam(String name) {
        return mParams.get(name);
    }

    /**
     * Get all parameters where the keys match the provided pattern.
     */
    protected List<Utils.Pair<String, String>> getParamsMatching(Pattern pattern) {
        return getParamsMatching(pattern, mParams);
    }

    /**
     * Parse the supplied tag string for parameters, and put them in {@param out}
     */
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
