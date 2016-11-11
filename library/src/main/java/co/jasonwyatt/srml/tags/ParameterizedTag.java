package co.jasonwyatt.srml.tags;

import android.os.Build;
import android.util.ArrayMap;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jason on 11/11/16.
 */

public abstract class ParameterizedTag extends Tag {
    private static final Pattern PARAMS_PATTERN = Pattern.compile("\\s+(([a-zA-Z_]+)=([^\\s}]+))*\\s*");
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

        Matcher m = PARAMS_PATTERN.matcher(tagStr);
        while (m.find()) {
            String paramName = m.group(2);
            String paramValue = m.group(3);
            mParams.put(paramName, paramValue);
        }
    }

    /**
     * Get a particular parameter from the params list supplied in the constructor.
     */
    protected String getParam(String name) {
        return mParams.get(name);
    }
}
