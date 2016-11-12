package co.jasonwyatt.srml.tags;

import android.os.Build;
import android.util.ArrayMap;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jason on 11/11/16.
 *
 * A ParameterizedTag is one which can contain an arbitrarily long list of parameters, defined as
 * name=value pairs after the tag name in the opening tag.
 */
public abstract class ParameterizedTag extends Tag {
    private static final Pattern PARAMS_PATTERN = Pattern.compile("\\s+(([a-zA-Z0-9_]+)=([^\\s}]+))");
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
     * Parse the supplied tag string for parameters, and put them in {@param out}
     */
    static void parseParams(String tagStr, Map<String, String> out) {
        Matcher m = PARAMS_PATTERN.matcher(tagStr);
        while (m.find()) {
            String paramName = m.group(2);
            String paramValue = m.group(3);
            out.put(paramName, paramValue);
        }
    }
}
